package com.hong.util.ldap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LdapUser {
 
	public String cn;
	public String sn;
	public String uid;
	public String userPassword;
	public String displayName;
	public String mail;
	public String description;

	public String uidNum;
	public String gidNum;
}