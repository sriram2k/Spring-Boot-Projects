package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.controller.CommentController;
import com.example.demo.controller.DefectController;
import com.example.demo.controller.DefectHisController;
import com.example.demo.controller.ProjectController;
import com.example.demo.controller.RoleController;
import com.example.demo.controller.ServiceController;
import com.example.demo.controller.TestcaseController;
import com.example.demo.controller.UserController;
import com.example.demo.model.CommentDesModel;
import com.example.demo.model.CommentModel;
import com.example.demo.model.DefectHisModel;
import com.example.demo.model.DefectModel;
import com.example.demo.model.History;
import com.example.demo.model.Project;
import com.example.demo.model.Requirement;
import com.example.demo.model.RoleModel;
import com.example.demo.model.ServiceModel;
import com.example.demo.model.Testcase;
import com.example.demo.model.UserModel;
import com.example.demo.repo.CommentRepo;
import com.example.demo.repo.DefectHisRepo;
import com.example.demo.repo.DefectRepo;
import com.example.demo.repo.ProjectRepo;
import com.example.demo.repo.RoleRepo;
import com.example.demo.repo.RoleValidation;
import com.example.demo.repo.SequenceGenerator;
import com.example.demo.repo.ServiceRepo;
import com.example.demo.repo.TestcaseRepo;
import com.example.demo.repo.UserRepo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class ProjectManagementSystemApplicationTests {

	@MockBean
	private DefectRepo drepo;
	
	@Autowired
	private DefectController dcontrol;
	
	@MockBean
	private CommentRepo crepo;
	
	@Autowired
	private CommentController ccontrol;
	
	@MockBean
	private DefectHisRepo hrepo;
	
	@Autowired
	private DefectHisController hcontrol;
	
	@MockBean
	RoleValidation valid;
	
	@MockBean
	SequenceGenerator seq;

	@Autowired
	ProjectController projectController;
	
	@Autowired
	TestcaseController testcaseController;

	@MockBean
	ProjectRepo projectRepo;
	
	@MockBean
	TestcaseRepo testRepo;
	
	@Autowired
	private RoleController rctrl;
	@MockBean
	private RoleRepo rrepo;
	
	@Autowired
	private UserController uctrl;
	@MockBean
	private UserRepo urepo;
	
	@Autowired
	private ServiceController sctrl;
	@MockBean
	private ServiceRepo srepo;
	
	String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVMTUiLCJleHAiOjQ1MzQ0ODUwNTg2NjYzODgsImlhdCI6MTYwNDU3NzAyNH0.1MBw4mJX_8re9td7tjTYsMXW-03ehGyjwUEOMx_sHBNXik1AwCdAgY2oCNlh2QbtR4vVe_cAsy2cxfwhsqglBA";
	

	
// ------------------------------------------------------------------------------------------------------------------
	
	@Test
	public void addDefectTest() {
		HashSet<String> attachmentLinks = new HashSet<String>();
		attachmentLinks.add("www.google.com");
		DefectModel defectObj = new DefectModel("D12", "active", new Date(),"P1","T1","Data could not be saved","Data saved properly","U15","U15","U15",new Date(), "New",1,"Requirement",attachmentLinks);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Defect : " , defectObj);
		when(valid.isValidUser("U15", "addDefect")).thenReturn(true);
		when(valid.getId(token)).thenReturn("U15");
		when(drepo.addDefects(defectObj, "U15")).thenReturn(map);
		assertEquals(map, dcontrol.addDefect(defectObj, token));
	}

	@Test
	public void getDefectsTest() {
		HashSet<String> attachmentLinks = new HashSet<String>();
		attachmentLinks.add("www.google.com");
		HashMap<String, Object> map = new HashMap<String, Object>();
		//DefectModel defectObj = new DefectModel("1", "Active", new Date(),"P1","T1","Login Failed","Login successful","E101","E101","E102",new Date(), "New",1,"Requirement",attachmentLinks);
		DefectModel defectObj = new DefectModel("D12", "active", new Date(),"P1","T1","Data could not be saved","Data saved properly","U15","U15","U15",new Date(), "New",1,"Requirement",attachmentLinks);
		List<DefectModel> l = new ArrayList<DefectModel>();
		l.add(defectObj);
		map.put("List of Defects : " , l);
		when(valid.isValidUser("U15", "displayDefects")).thenReturn(true);
		when(valid.getId(token)).thenReturn("U15");
		when(drepo.findAllValidDefects()).thenReturn(l);
		assertEquals(map,dcontrol.getDefects(token));
	}
	

	@Test
	public void getDefectByIdTest() {
		String defectId = "D101";
		HashSet<String> attachmentLinks = new HashSet<String>();
		attachmentLinks.add("www.google.com");
		HashMap<String, Object> map = new HashMap<String, Object>();
		DefectModel defectObj = new DefectModel("D12", "active", new Date(),"P1","T1","Data could not be saved","Data saved properly","U15","U15","U15",new Date(), "New",1,"Requirement",attachmentLinks);
		map.put("Defect : " , defectObj);
		when(valid.isValidUser("U15", "getDefectByDefectId")).thenReturn(true);
		when(valid.getId(token)).thenReturn("U15");
		when(seq.generateDefectSequence()).thenReturn(1L);
		when(drepo.findByDefectId(defectId))
			.thenReturn(defectObj);
		assertEquals(map, dcontrol.getDefectByDefectId(defectId,token));
	}

	@Test
	public void getDefectByProjectIdTest() {
		String projectId = "P101";
		HashSet<String> attachmentLinks = new HashSet<String>();
		attachmentLinks.add("www.google.com");
		HashMap<String, Object> map = new HashMap<String, Object>();
		DefectModel defectObj = new DefectModel("D12", "active", new Date(),"P1","T1","Data could not be saved","Data saved properly","U15","U15","U15",new Date(), "New",1,"Requirement",attachmentLinks);
		List<DefectModel> l = new ArrayList<DefectModel>();
		l.add(defectObj);
		map.put("List of Defects : " , l);
		when(valid.isValidUser("U15", "getDefectByProjectId")).thenReturn(true);
		when(valid.getId(token)).thenReturn("U15");
		when(seq.generateDefectSequence()).thenReturn(1L);
		when(drepo.findByProjectId(projectId))
			.thenReturn(l);
		assertEquals(map, dcontrol.getDefectByProjectId(projectId,token));
	}



	@Test
	public void removeDefectByDefectIdTest() {
		String defectId = "D101";
		HashSet<String> attachmentLinks = new HashSet<String>();
		attachmentLinks.add("www.google.com");
		HashMap<String, Object> map = new HashMap<String, Object>();
		DefectModel defectObj = new DefectModel("1", "Active", new Date(),"P1","T1","Login Failed","Login successful","E101","E101","E102",new Date(), "New",1,"Requirement",attachmentLinks);
		map.put("Defect : " , "Defect Deleted Successfully");
		when(valid.isValidUser("U15", "removeDefectByDefectId")).thenReturn(true);
		when(valid.getId(token)).thenReturn("U15");
		when(seq.generateDefectSequence()).thenReturn(1L);
		when(drepo.removeDefectByDefectId(defectId,"U15"))
				.thenReturn("Defect Deleted Successfully");
		assertEquals(map, dcontrol.removeDefectByDefectId(defectId, token));
	}

	
	
	@Test
	public void updateDefectTest() {
		HashSet<String> attachmentLinks = new HashSet<String>();
		attachmentLinks.add("www.google.com");
		String defectId = "D101";
		HashMap<String, Object> map = new HashMap<String, Object>();
		DefectModel defectObj = new DefectModel("1", "Active", new Date(),"P1","T1","Login Failed","Login successful","E101","E101","E102",new Date(), "New",1,"Requirement",attachmentLinks);
		map.put("Updated Defect : " , defectObj);
		when(valid.isValidUser("U15", "updateDefectByDefectId")).thenReturn(true);
		when(valid.getId(token)).thenReturn("U15");
		when(seq.generateDefectSequence()).thenReturn(1L);
		when(drepo.updateDefects(defectId, defectObj, "U15")).thenReturn(map);
		assertEquals(map, dcontrol.updateDefect(defectId,defectObj, token));
	}

// --------------------------------------------------------------------------------------------------------------


	@Test
	public void getDisplayCommentsTest() {
		ArrayList<CommentDesModel> l = new ArrayList<CommentDesModel>();
		l.add(new CommentDesModel("C101",new Date(),"E101","E102","Have a look into this fault"));
		HashMap<String, Object> map = new HashMap<String, Object>();
		CommentModel commentObj = new CommentModel("D101", "Active", l);
		ArrayList<CommentModel> list = new ArrayList<CommentModel>();
		list.add(commentObj);
		map.put("List of Comments : " , list);
		when(valid.isValidUser("U15", "displayComments")).thenReturn(true);
		when(valid.getId(token)).thenReturn("U15");
		when(crepo.findAll()).thenReturn(list);
		assertEquals(map, ccontrol.getDisplayComments(token));
	}
  
	@Test
	public void addChatTest() {
		ArrayList<CommentDesModel> l = new ArrayList<CommentDesModel>();
		l.add(new CommentDesModel("C101",new Date(),"E101","E102","Have a look into this fault"));
		l.add(new CommentDesModel("C102",new Date(),"E102","E101","I will look into it"));
		CommentDesModel cm = new CommentDesModel("C102",new Date(),"E102","E101","I will look into it");
		CommentModel commentObj = new CommentModel("D101", "Active", l);
		String defectId = "D101";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Updated Comment : " , commentObj);
		when(valid.isValidUser("U15", "addChat")).thenReturn(true);
		when(valid.getId(token)).thenReturn("U15");
		when(crepo.addChat(defectId,cm, "U15")).thenReturn(commentObj);
		assertEquals(map, ccontrol.addChat(cm,defectId, token));
	}

	@Test
	public void getCommentByDefectIdTest() {
		String defectId = "D101";
		ArrayList<CommentDesModel> l = new ArrayList<CommentDesModel>();
		l.add(new CommentDesModel("C101",new Date(),"E101","E102","Have a look into this fault"));
		CommentModel commentObj = new CommentModel("D101", "Active", l);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Comment : " , commentObj);
		when(valid.isValidUser("U15", "getCommentByDefectId")).thenReturn(true);
		when(valid.getId(token)).thenReturn("U15");
		when(crepo.findCommentByDefectId(defectId))
				.thenReturn(commentObj);
		assertEquals(map, ccontrol.getCommentByDefectId(defectId, token));
	}
	
// --------------------------------------------------------------------------------------------------------------

	
	@Test
	public void getDefectHistoryTest() {
		ArrayList<History> l = new ArrayList<History>();
		l.add(new History(1,"New",new Date(), new Date(), "E101"));
		DefectHisModel defectHisObj = new DefectHisModel("D101", "Active", l);
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<DefectHisModel> list = new ArrayList<DefectHisModel>();
		list.add(defectHisObj);
		map.put("History of Defects : ", list);
		when(valid.isValidUser("U15", "displayDefectHistory")).thenReturn(true);
		when(valid.getId(token)).thenReturn("U15");
		when(hrepo.findAll()).thenReturn(list);
		assertEquals(map, hcontrol.getDefectHistory(token));
	}

	@Test
	public void getDefectHisByDefectIdTest() {
		String defectId = "D1";
		ArrayList<History> l = new ArrayList<History>();
		l.add(new History(1,"New",new Date(), new Date(), "U15"));
		l.add(new History(2,"open", new Date(), new Date(), "U18"));
		DefectHisModel defectHisObj = new DefectHisModel("D1", "active", l);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Defect History : ", defectHisObj);
		when(valid.isValidUser("U15", "getDefectHistoryByDefectId")).thenReturn(true);
		when(valid.getId(token)).thenReturn("U15");
		when(hrepo.findDefectHistoryByDefectId(defectId))
				.thenReturn(defectHisObj);
		//HashMap<String, Object> m1 = map.put("Defect History : ", hcontrol.getDefectHisByDefectId(token, defectId));
		assertEquals(map, hcontrol.getDefectHisByDefectId(token, defectId));
	}

// -----------------------------------------------------------------------------------------------------------------



@Test
public void addUserTest() {
	Set<String> ur = new HashSet();
	ur.add("developer");ur.add("tester");
	UserModel userObj = new UserModel("U11","john2k","John@2000","john","babu","P101","johnk@gmail.com","9896754345","U14","U14",new Date(),"29-01-2020","29-01-2020",ur,"active");
	HashMap<String,Object> hm =new HashMap<>();
	hm.put("data",userObj);

	when(urepo.save(userObj,"U14")).thenReturn(hm);
	when(valid.isValidUser("U14", "addUser")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U14");
	when(seq.generateUserSequence()).thenReturn(11L);
	assertEquals(hm, uctrl.insertUserRecord(userObj, token));
}

@Test
public void getAllUsersTest() {
	Set<String> set1 = new HashSet();
	set1.add("developer");set1.add("tester");
	Set<String> set2 = new HashSet();
	set2.add("developer");set2.add("admin");set1.add("manager");
	
	UserModel um1=new UserModel("U11","john2k","john2000","john","babu","P101","johnk@gmail.com","9896754345","U14","U14",new Date(),"29-01-2020","active",set1,"active");
	UserModel um2=new UserModel("U12","jack2k","jack@gym","jack","son","P102","jackson@gmail.com","9444754345","U14","U14",new Date(),"09-11-2020","active",set2,"active");
	
	HashMap<String,Object> hm =new HashMap<>();
	Set<Object> set3 = new HashSet();
	set3.add(um1);set3.add(um2);
	hm.put("Users",set3);
	
	when(urepo.findAll()).thenReturn((hm));
	when(valid.isValidUser("U14", "getUsers")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U14");
	
	assertEquals(1, uctrl.getAllUserDetails(token).size());
}

@Test
public void getOneUserTest() {
	String uid = "U12";
	Set<String> set = new HashSet();
	set.add("developer");set.add("tester");
	UserModel um1=new UserModel("U11","john2k","john2000","john","babu","P101","johnk@gmail.com","9896754345","U14","U14",new Date(),"29-01-2020","active",set,"active");
	
	HashMap<String,Object> hm =new HashMap<>();
	hm.put("Users",um1);
	
	when(urepo.findOne(uid)).thenReturn((hm));
	when(valid.isValidUser("U14", "getUser")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U14");
	
	assertEquals(1, uctrl.getOneUserDetails(uid, token).size());
}

@Test
public void getOneUsersByRoleTest() {
	String rolename = "developer";
	Set<String> set = new HashSet();
	set.add("developer");set.add("tester");
	Set<String> set2 = new HashSet();
	set2.add("developer");set2.add("admin");set2.add("manager");
	
	UserModel um1=new UserModel("U11","john2k","john2000","john","babu","P101","johnk@gmail.com","9896754345","U14","U14",new Date(),"29-01-2020","active",set,"active");
	UserModel um2=new UserModel("U12","jack2k","jack@gym","jack","son","P102","jackson@gmail.com","9444754345","U14","U14",new Date(),"09-11-2020","active",set2,"active");
	
	HashMap<String,Object> hm =new HashMap<>();
	Set<Object> set3 = new HashSet();
	set3.add(um1);set3.add(um2);
	hm.put("Users",set3);
	
	when(urepo.findAllRoles(rolename)).thenReturn(hm);
	when(valid.isValidUser("U14", "getUsersByRole")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U14");
	assertEquals(1,uctrl.geUserDetailsByRoleName(rolename,token).size());
}

@Test
public void getOneUsersByProjectTest() {
	String projid = "P104";
	Set<String> set = new HashSet();
	set.add("developer");set.add("tester");
	Set<String> set2 = new HashSet();
	set2.add("developer");set2.add("admin");set2.add("manager");
	
	UserModel um1=new UserModel("U11","john2k","john@2000","john","babu","P104","johnk@gmail.com","9896754345","U14","U14",new Date(),"29-01-2020","active",set,"active");
	UserModel um2=new UserModel("U12","jack2k","jack@gym","jack","son","P104","jackson@gmail.com","9444754345","U14","U14",new Date(),"09-11-2020","active",set2,"active");
	
	HashMap<String,Object> hm =new HashMap<>();
	Set<Object> set3 = new HashSet();
	set3.add(um1);set3.add(um2);
	hm.put("Users",set3);
	
	when(urepo.findAllProjects(projid)).thenReturn(hm);
	when(valid.isValidUser("U14", "getUsersByProject")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U14");
	assertEquals(1,uctrl.geUserDetailsByProjectId(projid, token).size());
}

@Test
public void updateUserTest() {
	String uid = "U12";
	Set<String> set = new HashSet();
	set.add("developer");set.add("tester");
	Set<String> set1 = new HashSet();
	set1.add("developer");set1.add("admin");set1.add("manager");
	UserModel userObj1 = new UserModel("U11","john2k","John200@","john","babu","P101","johnk@gmail.com","9896754345","U14","U14",new Date(),"29-01-2020","29-01-2020",set,"active");
	UserModel userObj2=new UserModel("U12","jack2k","Jack@gym2k","jack","son","P102","jackson@gmail.com","9444754345","U14","U14",new Date(),"09-11-2020","29-01-2020",set1,"active");
	
	HashMap<String,Object> hm1 =new HashMap<>();
	hm1.put("Users",userObj1);
	HashMap<String,Object> hm2 =new HashMap<>();
	hm2.put("Users",userObj2);
	
	when(urepo.updateOneUser(userObj1, uid,"U14")).thenReturn(hm2);
	when(valid.isValidUser("U14", "updateUser")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U14");
	assertEquals(hm2,uctrl.updateOneUserDetails(userObj1, uid, token));
}

@Test
public void deleteUserTest() {
	String uid = "U12";
	uctrl.deleteOneUserDetails(uid,token);
	Set<String> set = new HashSet();
	set.add("developer");set.add("tester");
	UserModel userObj1 = new UserModel("U11","john2k","john2000","john","babu","P101","johnk@gmail.com","9896754345","U14","U14",new Date(),"29-01-2020","active",set,"active");
	HashMap<String,Object> hm1 =new HashMap<>();
	hm1.put("Users",userObj1);
	
	when(urepo.deleteOne(uid,"U14")).thenReturn(hm1);
	when(valid.isValidUser("U14", "deleteUser")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U14");
	assertEquals(hm1,uctrl.deleteOneUserDetails(uid, token));
}


//Permission Services	
@Test
public void addServiceTest() {
	Set<String> set = new HashSet();
	set.add("developer");set.add("tester");
	ServiceModel serObj = new ServiceModel("addService",set,"U14","U14",new Date());
	HashMap<String,Object> hm1 =new HashMap<>();
	hm1.put("Inserted",serObj);
	
	when(srepo.save(serObj,"U14")).thenReturn(hm1);
	when(valid.isValidUser("U14", "addService")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U14");
	
	assertEquals(hm1, sctrl.insertService(serObj,token));
}

@Test
public void getAllServicesTest() {
	Set<String> set = new HashSet();
	set.add("developer");set.add("tester");
	Set<String> set1 = new HashSet();
	set1.add("developer");set1.add("admin");set1.add("manager");
	ServiceModel serObj1 = new ServiceModel("addService",set,"U14","U14",new Date());
	ServiceModel serObj2 = new ServiceModel("updateRole",set1,"U14","U14",new Date());
	
	HashMap<String,Object> hm =new HashMap<>();
	Set<Object> set3 = new HashSet();
	set3.add(serObj2);set3.add(serObj1);
	hm.put("Services",set3);
	
	when(srepo.findAll()).thenReturn((hm));
	when(valid.isValidUser("U14", "getServices")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U14");
	
	assertEquals(1, sctrl.getAllServices(token).size());
}

@Test
public void getOneServiceTest() {
	Set<String> set = new HashSet();
	set.add("developer");set.add("tester");
	ServiceModel serObj = new ServiceModel("addService",set,"U14","U14",new Date());
	String sername = "addService";
	
	HashMap<String,Object> hm =new HashMap<>();
	hm.put("Services",serObj);
	
	when(srepo.findByService(sername)).thenReturn(hm);
	when(valid.isValidUser("U14", "getServiceByName")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U14");
	assertEquals(hm,sctrl.getServiceByName(sername,token));
}

@Test
public void getOneServiceByRoleTest() {
	Set<String> set = new HashSet();
	set.add("developer");set.add("tester");
	String rolename = "developer";
	
	ServiceModel serObj = new ServiceModel("addService",set,"U14","U14",new Date());
	HashMap<String,Object> hm =new HashMap<>();
	hm.put("Services",serObj);
	
	when(srepo.findByRole(rolename)).thenReturn(hm);
	when(valid.isValidUser("U14", "getServiceByRole")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U14");
	assertEquals(hm,sctrl.getServiceByRole(rolename,token));
	
}


@Test
public void updateServiceTest() {
	String sername = "addService";
	Set<String> set = new HashSet();
	set.add("developer");set.add("tester");
	Set<String> set1 = new HashSet();
	set1.add("developer");set1.add("admin");set1.add("manager");
	ServiceModel serObj1 =new ServiceModel(sername,set,"U14","U14",new Date());
	ServiceModel serObj2 =new ServiceModel(sername,set1,"U14","U14",new Date());
	
	HashMap<String,Object> hm1 =new HashMap<>();
	hm1.put("Users",serObj1);
	HashMap<String,Object> hm2 =new HashMap<>();
	hm2.put("Users",serObj2);
	
	when(srepo.updatePermissions(serObj1, sername,"U14")).thenReturn(hm2);
	when(valid.isValidUser("U14", "updatePermission")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U14");
	assertEquals(hm2,sctrl.updatePermission(serObj1, sername, token));
	
	
}


@Test
public void deleteServiceTest() {
	String sername = "addService";
	Set<String> set = new HashSet();
	set.add("developer");set.add("tester");
	
	ServiceModel serObj2 =new ServiceModel(sername,set,"U14","U14",new Date());
	HashMap<String,Object> hm1 =new HashMap<>();
	hm1.put("Users",serObj2);
	
	when(srepo.deleteService(sername)).thenReturn(hm1);
	when(valid.isValidUser("U14", "deleteService")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U14");
	assertEquals(hm1,sctrl.deleteService(sername, token));
}

//Role Services
@Test
public void addRoleTest() {

	RoleModel roleObj = new RoleModel("R1","developer","active","U11","U11",new Date());
	HashMap<String,Object> hm1 =new HashMap<>();
	hm1.put("Inserted",roleObj);
	
	when(rrepo.save(roleObj,"U11")).thenReturn(hm1);
	when(valid.isValidUser("U11", "addRole")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U11");
	when(seq.generateUserSequence()).thenReturn(11L);
	
	assertEquals(hm1, rctrl.insertNewRole(roleObj,token));
}

@Test
public void getAllRolesTest() {
	RoleModel roleObj1 = new RoleModel("R1","developer","active","U11","U11",new Date());
	RoleModel roleObj2 = new RoleModel("R2","admin","active","U11","U11",new Date());
	HashMap<String,Object> hm1 =new HashMap<>();
	String sername = "addService";
	Set<Object> set = new HashSet();
	set.add(roleObj1);set.add(roleObj2);
	hm1.put("Inserted",set);
	when(rrepo.findAll()).thenReturn(hm1);
	when(valid.isValidUser("U11", "getAllRoles")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U11");
	assertEquals(hm1, rctrl.getAllRoles(token));
}
//
@Test
public void getOneRoleTest() {
	RoleModel r=new RoleModel("R1","developer","active","U11","U11",new Date());
	String roleid = "R101";
	HashMap<String,Object> hm1 =new HashMap<>();
	hm1.put("RoleDetail",r);
	when(rrepo.findOne(roleid)).thenReturn(hm1);
	when(valid.isValidUser("U11", "getRole")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U11");
	assertEquals(hm1,rctrl.getOneRole(roleid,token));
}

@Test
public void updateRoleTest() {
	RoleModel robj1=new RoleModel("R1","developer","active","U11","U11",new Date());
	RoleModel robj2=new RoleModel("R3","manager","active","U11","U11",new Date());
	String roleid = "R101";
	
	HashMap<String,Object> hm1 =new HashMap<>();
	hm1.put("Updated",robj1);
	HashMap<String,Object> hm2 =new HashMap<>();
	hm2.put("Updated",robj2);
	
	when(rrepo.updateOneRole(robj1,roleid,"U11")).thenReturn(hm2);
	when(valid.isValidUser("U11", "updateRole")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U11");
	assertEquals(hm2,rctrl.updateOneRole(robj1, roleid,token));
}

@Test
public void deleteRoleTest() {
	String roleid = "R1";
	RoleModel r=new RoleModel("R1","developer","active","U11","U11",new Date());
	HashMap<String,Object> hm1 =new HashMap<>();
	hm1.put("Roles",r);
	
	when(rrepo.deleteOne(roleid,"U11")).thenReturn(hm1);
	when(valid.isValidUser("U11", "deleteRole")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U11");
	assertEquals(hm1,rctrl.deleteOneRole(roleid, token));
}


//--------------------------------------------------------------------------------------------------------------------------------------



//-----------------------------------------------------------------------------------------------------------------

@Test
void saveProject() {
	List<Requirement> r = new ArrayList<>();
	r.add( new Requirement("P101R1","splash screen","New"));
	r.add(new Requirement("P101R2","login screen","New"));
	Project pr=new Project("P101","Android App","apk for login",new Date(),new Date(),"March","New","U1","U1",new Date(),r);
	when(projectRepo.saveProject(pr)).thenReturn(pr);
	when(valid.isValidUser("U15", "addProject")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U15");
	when(seq.generateSequenceforProject()).thenReturn(101L);
	HashMap<String,Object> hm = new HashMap<>();
	hm.put("AddedProject", pr);
	assertEquals(hm,projectController.saveProject(pr,token));
}

@Test
void getAllProjects() {
	List<Requirement> r = new ArrayList<>();
	List<Project> p = new ArrayList<>();
	HashMap<String,Object> hm = new HashMap<>();
	r.add( new Requirement("P101R1","splash screen","New"));
	r.add(new Requirement("P101R2","login screen","New"));
	Project pr=new Project("P101","Android App","apk for login",new Date(),new Date(),"March","New","U1","U1",new Date(),r);
	p.add(pr);
	hm.put("Projects", p);
	when(projectRepo.getAllProjects()).thenReturn(p);
	when(valid.isValidUser("U15", "getAllProjects")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U15");
	assertEquals(hm,projectController.getAllProjects(token));
}

@Test
void getProjectByPid() {
	String pid = "P101";
	List<Requirement> r = new ArrayList<>();
	HashMap<String,Object> hm = new HashMap<>();
	r.add( new Requirement("P101R1","splash screen","New"));
	r.add(new Requirement("P101R2","login screen","New"));
	Project pr=new Project("P101","Android App","apk for login",new Date(),new Date(),"March","New","U1","U1",new Date(),r);
	hm.put("ProjectDetails", pr);
	when(projectRepo.getProjectByPid(pid)).thenReturn(pr);
	when(valid.isValidUser("U1", "getProject")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U1");
	assertEquals(hm,projectController.getProjectByPid(pid,token));
}

@Test
void updateProject() {
	String pid = "P101";
	String eid = "U1";
	List<Requirement> r = new ArrayList<>();
	HashMap<String,Object> hm = new HashMap<>();
	r.add( new Requirement("P101R1","splash screen","New"));
	r.add(new Requirement("P101R2","login screen","New"));
	Project pr=new Project("P101","Android App","apk for login",new Date(),new Date(),"March","New","U1","U1",new Date(),r);
	hm.put("Updated Project", pr);
	when(projectRepo.updateProject(pr,pid,eid)).thenReturn(pr);
	when(valid.isValidUser("U1", "editProject")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U1");
	assertEquals(hm,projectController.updateProject(pr,pid,token));
}

@Test 
void deleteProject(){
	String eid = "U1";
	String pid = "P101";
	List<Requirement> r = new ArrayList<>();
	HashMap<String,Object> hm = new HashMap<>();
	r.add( new Requirement("P101R1","splash screen","New"));
	r.add(new Requirement("P101R2","login screen","New"));
	Project pr=new Project("P101","Android App","apk for login",new Date(),new Date(),"March","New","U1","U1",new Date(),r);
	hm.put("Deleted Project", pr);
	when(projectRepo.deleteProject(pid,eid)).thenReturn(pr);
	when(valid.isValidUser("U1", "deleteProject")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U1");
	assertEquals(hm,projectController.deleteProject(pid,token));
}

@Test
void editRequirement() {
	String rid = "P101R1";
	String eid = "U1";
	List<Requirement> r = new ArrayList<>();
	Requirement newReq = new Requirement("P101R1","splash screen","New");
	HashMap<String,Object> hm = new HashMap<>();
	r.add( newReq);
	r.add(new Requirement("P101R2","login screen","New"));
	Project pr=new Project("P101","Android App","apk for login",new Date(),new Date(),"March","New","U1","U1",new Date(),r);
	hm.put("Edited Requirement", pr);
	when(projectRepo.updateRequirement(rid, newReq,eid)).thenReturn(pr);
	when(valid.isValidUser("U1", "editRequirement")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U1");
	assertEquals(hm,projectController.updateRequirement(newReq, rid, token));
}

@Test
void addRequirement() {
	String pid = "P101";
	String eid = "U1";
	List<Requirement> r = new ArrayList<>();
	Requirement newReq = new Requirement("P101R2","splash screen","New");
	HashMap<String,Object> hm = new HashMap<>();
	r.add(newReq);
	r.add(new Requirement("P101R1","login screen","New"));
	Project pr=new Project("P101","Android App","apk for login",new Date(),new Date(),"March","New","U1","U1",new Date(),r);
	hm.put("Added Requirement", pr);
	when(projectRepo.addRequirement(newReq, pid,eid)).thenReturn(pr);
	when(valid.isValidUser("U1", "addRequirement")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U1");
	assertEquals(hm,projectController.addRequirement(newReq, pid, token));
}

@Test
void deleteRequirement() {
	String rid = "P101R2";
	String eid = "U1";
	List<Requirement> r = new ArrayList<>();
	Requirement delReq = new Requirement("P101R2","splash screen","InActive");
	HashMap<String,Object> hm = new HashMap<>();
	r.add(delReq);
	r.add(new Requirement("P101R1","login screen","New"));
	Project pr=new Project("P101","Android App","apk for login",new Date(),new Date(),"March","New","U1","U1",new Date(),r);
	hm.put("Deleted Requirement", pr);
	when(projectRepo.deleteRequirement(rid,eid)).thenReturn(pr);
	when(valid.isValidUser("U1", "deleteRequirement")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U1");
	assertEquals(hm,projectController.deleteRequirement(rid, token));
}

/*
 * 
 * TestcaseController
 * 
 */

@Test
void addTestcase() {
	HashMap<String,Object> hm = new HashMap<>();
	List<Requirement> r = new ArrayList<>();
	r.add(new Requirement("P1R1","login screen","New"));
	Project pr=new Project("P1","Android App","apk for login",new Date(),new Date(),"March","New","U1","U1",new Date(),r);
	Testcase t = new Testcase("P1","P1R1","T1","Testing toast","A short msg should appear after login","Welcome in Toast", "XXX","OnHold","U1","U1",new Date());
	when(valid.isValidUser("U1", "addTestcase")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U1");
	when(projectRepo.getProjectByPid(t.getProjectId())).thenReturn(pr);
	when(seq.generateSequenceforTestcase()).thenReturn(1L);
	hm.put("Added Testcase", t);
	when(testRepo.save(t)).thenReturn(t);
	assertEquals(hm,testcaseController.addTestcase(t, token));
}

@Test
void getAllTestcases() {
	HashMap<String,Object> hm = new HashMap<>();
	List<Testcase> testcases = new ArrayList<>();
	Testcase t = new Testcase("P101","P101R1","T1","Testing toast","A short msg should appear after login","Welcome in Toast", "XXX","OnHold","U1","U1",new Date());
	when(valid.isValidUser("U15", "getTestcases")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U15");
	testcases.add(t);
	hm.put("Testcases", testcases);
	when(testRepo.getAlltestcases()).thenReturn(testcases);
	assertEquals(hm,testcaseController.getAllTestcases(token));
}

@Test
void getAllTestcasesByRid() {
	String rid = "P101R1";
	HashMap<String,Object> hm = new HashMap<>();
	List<Testcase> testcases = new ArrayList<>();
	Testcase t = new Testcase("P101","P101R1","T1","Testing toast","A short msg should appear after login","Welcome in Toast", "XXX","OnHold","U1","U1",new Date());
	when(valid.isValidUser("U15", "getTestcasesByRid")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U15");
	testcases.add(t);
	hm.put("Testcases", testcases);
	when(testRepo.getAlltestcasesByRid(rid)).thenReturn(testcases);
	assertEquals(hm,testcaseController.getAllTestcasesByRid(rid,token));
}

@Test
void getAllTestcasesByPid() {
	String pid = "P101";
	HashMap<String,Object> hm = new HashMap<>();
	List<Testcase> testcases = new ArrayList<>();
	Testcase t = new Testcase("P101","P101R1","T1","Testing toast","A short msg should appear after login","Welcome in Toast", "XXX","OnHold","U1","U1",new Date());
	when(valid.isValidUser("U15", "getTestcasesByPid")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U15");
	testcases.add(t);
	hm.put("Testcases", testcases);
	when(testRepo.getAlltestcasesByPid(pid)).thenReturn(testcases);
	assertEquals(hm,testcaseController.getAllTestcasesByPid(pid,token));
}

@Test
void editTestcase() {
	String tid = "T2";
	String eid = "U15";
	HashMap<String,Object> hm = new HashMap<>();
	Testcase t2 = new Testcase("P101","P101R1","T1","Testing toast","A short msg should appear after login","Welcome in Toast", "XXX","OnHold","U1","U1",new Date());
	when(valid.isValidUser("U15", "editTestcase")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U15");
	hm.put("Edited Testcase", t2);
	when(testRepo.editTestcase(tid, t2,eid)).thenReturn(t2);
	assertEquals(hm,testcaseController.editTestcase(tid, t2, token));
}

@Test
void deleteTestcase() {
	String tid = "T2";
	String eid = "U15";
	HashMap<String,Object> hm = new HashMap<>();
	Testcase t2 = new Testcase("P101","P101R1","T1","Testing toast","A short msg should appear after login","Welcome in Toast", "XXX","OnHold","U1","U1",new Date());
	when(valid.isValidUser("U15", "deleteTestcase")).thenReturn(true);
	when(valid.getId(token)).thenReturn("U15");
	hm.put("Deleted Testcase", t2);
	when(testRepo.deleteTestcase(tid,eid)).thenReturn(t2);
	assertEquals(hm,testcaseController.deleteTestcase(tid,token));
}
//-----------------------------------------------------------------------------------------------------------------

}
