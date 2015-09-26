package library.daos;

import java.util.ArrayList;
import java.util.List;

import library.interfaces.daos.IMemberDAO;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IMember;

public class MemberDAO implements IMemberDAO{
	
	private IMemberHelper iMemberHelper_;	
	private int nextMemberId_;
	private List<IMember> memberList_;
	
	public MemberDAO(IMemberHelper iMemberHelper)
	throws IllegalArgumentException
	{
		if(iMemberHelper == null)
		{
			throw new IllegalArgumentException("Error, IMemberHelper cannot be null.");
		}
		else{
			iMemberHelper_ = iMemberHelper;
			nextMemberId_ = 0;
		}
	}

	public IMember addMember(String firstName, String lastName,
			String contactPhone, String emailAddress) {
		if(firstName == null)
		{
			throw new IllegalArgumentException("Error, first name cannot be null.");
		}
		if(firstName == "")
		{
			throw new IllegalArgumentException("Error, first name cannot be blank.");
		}
		if(lastName == null)
		{
			throw new IllegalArgumentException("Error, last name cannot be null.");
		}
		if(lastName == "")
		{
			throw new IllegalArgumentException("Error, last name cannot be blank.");
		}
		if(contactPhone == null)
		{
			throw new IllegalArgumentException("Error, phone number cannot be null.");
		}
		if(contactPhone == "")
		{
			throw new IllegalArgumentException("Error, phone number cannot be blank.");
		}
		if(emailAddress == null)
		{
			throw new IllegalArgumentException("Error, email address cannot be null.");
		}
		if(emailAddress == "")
		{
			throw new IllegalArgumentException("Error, email address cannot be blank.");
		}		
		
		IMember member = null;
		
		if(((firstName != null) && (firstName != "")) && ((lastName != null) && (lastName != "")) && ((contactPhone != null) && (contactPhone != "")) && ((emailAddress != null) && (emailAddress != "")))
		{
			member = iMemberHelper_.makeMember(firstName, lastName, contactPhone, emailAddress, nextMemberId_);
			memberList_.add(member);
			nextMemberId_ += 1;
		}
		return member;
	}

	public IMember getMemberByID(int id) {
		IMember member = null;
		for(IMember i : memberList_){
			if(i.getID() == id)
			{
				return member = i;
			}
		}
		return member;
	}

	public List<IMember> listMembers() {
		return memberList_;
	}

	public List<IMember> findMembersByLastName(String lastName) {
		ArrayList<IMember> listMatchingLastName = new ArrayList<IMember>();
		for(IMember i : memberList_){
			if(i.getLastName() == lastName)
			{
				listMatchingLastName.add(i);
			}
		}
		return listMatchingLastName;
	}

	@Override
	public List<IMember> findMembersByEmailAddress(String emailAddress) {
		ArrayList<IMember> listMatchingEmailAddress = new ArrayList<IMember>();
		for(IMember i : memberList_){
			if(i.getEmailAddress() == emailAddress)
			{
				listMatchingEmailAddress.add(i);
			}
		}
		return listMatchingEmailAddress;
	}

	public List<IMember> findMembersByNames(String firstName, String lastName) {
		ArrayList<IMember> listMatchingName = new ArrayList<IMember>();
		for(IMember i : memberList_){
			if((i.getLastName() == firstName) && (i.getLastName() == lastName))
			{
				listMatchingName.add(i);
			}
		}
		return listMatchingName;
	}

}
