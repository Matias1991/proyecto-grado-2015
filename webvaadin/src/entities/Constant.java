package entities;

public class Constant {

	public class UserType
	{
		public static final int USER_TYPE_ADMIN = 1;
		public static final int USER_TYPE_PARTNER = 2;
		public static final int USER_TYPE_MANAGER = 3;
	}
	
	public class Module
	{
		public static final String USERS = "Usuarios";
		public static final String EMPLOYEES = "Empleados";
		public static final String CATEGORIES = "Rubros";
		public static final String CHARGES = "Cobros";
		public static final String PROJECTS = "Proyectos";
	}
	
	public class View
	{
		public static final String LOGIN = "LoginView";
		
		// Usuarios
		public static final String CREATEUSER = "CreateUserView";
		public static final String UPDATEPROFILEUSER = "ModifyProfileView";
		public static final String DELETEUSERS = "DeleteUsersView";
		public static final String UPDATEUSER = "ModifyUserView";
		public static final String CATALOGUSERS = "CatalogUsersView";
		public static final String FORGOTPASSWORD = "ForgotPaswordView";
		public static final String CHANGEPASSWORD = "ChangePasswordView";
		public static final String RESETPASSWORD = "ResetPasswordView";
		public static final String UNLOCKUSER = "UnlockUserView";

		// Empleados
		public static final String CATALOGEMPLOYEES = "CatalogEmployeesView";
		public static final String CREATEEMPLOYEE = "CreateEmployeeView";
		public static final String DELETEEMPLOYEE = "DeleteEmployeeView";
		public static final String UPDATEEMPLOYEE = "UpdateEmployeeView";

		// Rubros
		public static final String CREATECATEGORY = "CreateCategoryView";
		public static final String CATEGORIES = "CatalogCategoriesView";
		public static final String DELETECATEGORY = "DeleteCategoriesView";
		public static final String UPDATECATEGORY = "UpdateCategoryView";

		//Facturas
		public static final String CREATEBILL = "CreateBillView";
		public static final String BILLS = "CatalogBillsView";
		public static final String UPDATEBILL = "UpdateBillView";
		public static final String DELETEBILLS = "DeleteBillsView";
		
		//Cobros
		public static final String CREATECHARGE = "CreateChargeView";
		public static final String UPDATECHARGE = "UpdateChargeView";
		public static final String DELETECHARGES = "DeleteChargesView";
		public static final String CHARGES = "CatalogChargesView";
		
		//Proyectos
		public static final String CATALOGPROJECTS = "CatalogProjectView";
		public static final String UPDATEPROJECT = "UpdateProjectView";
		public static final String CREATEPROJECT = "CreateProjectView";	
		public static final String CLOSEPROJECT = "DeleteProjectView";
	}
}
