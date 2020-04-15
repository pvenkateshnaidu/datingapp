package com.datingapp.springjwt.payload.request;

import java.util.Set;

import javax.validation.constraints.*;


 
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    private String gender;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private String file;
	private String profilefor;
	private String religion;
	private String dob;
	private String maritalStatus;
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
    public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}
	 public String getGender() {
	        return gender;
	    }
	    public void setGender(String gender) {
	        this.gender = gender;
	    }
	
	public String getProfilefor() {
		return profilefor;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public void setProfilefor(String profilefor) {
		this.profilefor = profilefor;
	}
	public String getReligion() {
		return religion;
	}
    public void setPassword(String password) {
        this.password = password;
    }
  
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }

	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}

	/**
	 * @param maritalStatus the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}


}
