package com.datingapp.springjwt.services.impl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.datingapp.springjwt.services.S3Services;
 
@Service
public class S3ServicesImpl implements S3Services {
	
	private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);
	
	@Autowired
	private AmazonS3 s3client;
 
	@Value("${gkz.s3.bucket}")
	private String bucketName;
 
	@Override
	public ByteArrayOutputStream downloadFile(String keyName) {
		try {
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
            
            InputStream is = s3object.getObjectContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, len);
            }
            
            return baos;
		} catch (IOException ioe) {
			logger.error("IOException: " + ioe.getMessage());
        } catch (AmazonServiceException ase) {
        	logger.info("sCaught an AmazonServiceException from GET requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
			throw ase;
        } catch (AmazonClientException ace) {
        	logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
            throw ace;
        }
		
		return null;
	}
 
	@Override
//	public void uploadFile(String keyName, MultipartFile file) {
	public void uploadFile(String keyName, String base64Data) {
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			byte[] bI = org.apache.commons.codec.binary.Base64.decodeBase64((base64Data.substring(base64Data.indexOf(",")+1)).getBytes());

			InputStream fis = new ByteArrayInputStream(bI);
			metadata.setContentLength(bI.length);
			metadata.setContentType("image/png");
			metadata.setCacheControl("public, max-age=31536000");
			
			s3client.putObject(bucketName+"/images", keyName, fis, metadata);
			s3client.setObjectAcl(bucketName+"/images", keyName, CannedAccessControlList.PublicRead);
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
			throw ase;
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
            throw ace;
        }
	}
}
