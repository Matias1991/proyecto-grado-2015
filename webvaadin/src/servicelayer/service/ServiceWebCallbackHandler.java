
/**
 * ServiceWebCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

    package servicelayer.service;

    /**
     *  ServiceWebCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ServiceWebCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ServiceWebCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ServiceWebCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for updateUser method
            * override this method for handling normal response from updateUser operation
            */
           public void receiveResultupdateUser(
                    servicelayer.service.ServiceWebStub.UpdateUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateUser operation
           */
            public void receiveErrorupdateUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getProject method
            * override this method for handling normal response from getProject operation
            */
           public void receiveResultgetProject(
                    servicelayer.service.ServiceWebStub.GetProjectResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getProject operation
           */
            public void receiveErrorgetProject(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getUsersByStatus method
            * override this method for handling normal response from getUsersByStatus operation
            */
           public void receiveResultgetUsersByStatus(
                    servicelayer.service.ServiceWebStub.GetUsersByStatusResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getUsersByStatus operation
           */
            public void receiveErrorgetUsersByStatus(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCategoriesByDescription method
            * override this method for handling normal response from getCategoriesByDescription operation
            */
           public void receiveResultgetCategoriesByDescription(
                    servicelayer.service.ServiceWebStub.GetCategoriesByDescriptionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCategoriesByDescription operation
           */
            public void receiveErrorgetCategoriesByDescription(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateBill method
            * override this method for handling normal response from updateBill operation
            */
           public void receiveResultupdateBill(
                    servicelayer.service.ServiceWebStub.UpdateBillResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateBill operation
           */
            public void receiveErrorupdateBill(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getBills method
            * override this method for handling normal response from getBills operation
            */
           public void receiveResultgetBills(
                    servicelayer.service.ServiceWebStub.GetBillsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getBills operation
           */
            public void receiveErrorgetBills(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unlockUser method
            * override this method for handling normal response from unlockUser operation
            */
           public void receiveResultunlockUser(
                    servicelayer.service.ServiceWebStub.UnlockUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unlockUser operation
           */
            public void receiveErrorunlockUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updatedEmployed method
            * override this method for handling normal response from updatedEmployed operation
            */
           public void receiveResultupdatedEmployed(
                    servicelayer.service.ServiceWebStub.UpdatedEmployedResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updatedEmployed operation
           */
            public void receiveErrorupdatedEmployed(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getProjectsLiquidations method
            * override this method for handling normal response from getProjectsLiquidations operation
            */
           public void receiveResultgetProjectsLiquidations(
                    servicelayer.service.ServiceWebStub.GetProjectsLiquidationsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getProjectsLiquidations operation
           */
            public void receiveErrorgetProjectsLiquidations(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for existUser method
            * override this method for handling normal response from existUser operation
            */
           public void receiveResultexistUser(
                    servicelayer.service.ServiceWebStub.ExistUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from existUser operation
           */
            public void receiveErrorexistUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getEmployees method
            * override this method for handling normal response from getEmployees operation
            */
           public void receiveResultgetEmployees(
                    servicelayer.service.ServiceWebStub.GetEmployeesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getEmployees operation
           */
            public void receiveErrorgetEmployees(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCategoriesByDate method
            * override this method for handling normal response from getCategoriesByDate operation
            */
           public void receiveResultgetCategoriesByDate(
                    servicelayer.service.ServiceWebStub.GetCategoriesByDateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCategoriesByDate operation
           */
            public void receiveErrorgetCategoriesByDate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCategory method
            * override this method for handling normal response from getCategory operation
            */
           public void receiveResultgetCategory(
                    servicelayer.service.ServiceWebStub.GetCategoryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCategory operation
           */
            public void receiveErrorgetCategory(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getBillsByProject method
            * override this method for handling normal response from getBillsByProject operation
            */
           public void receiveResultgetBillsByProject(
                    servicelayer.service.ServiceWebStub.GetBillsByProjectResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getBillsByProject operation
           */
            public void receiveErrorgetBillsByProject(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCompanyLiquidationPreview method
            * override this method for handling normal response from getCompanyLiquidationPreview operation
            */
           public void receiveResultgetCompanyLiquidationPreview(
                    servicelayer.service.ServiceWebStub.GetCompanyLiquidationPreviewResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCompanyLiquidationPreview operation
           */
            public void receiveErrorgetCompanyLiquidationPreview(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for login method
            * override this method for handling normal response from login operation
            */
           public void receiveResultlogin(
                    servicelayer.service.ServiceWebStub.LoginResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from login operation
           */
            public void receiveErrorlogin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCompanyLiquidations method
            * override this method for handling normal response from getCompanyLiquidations operation
            */
           public void receiveResultgetCompanyLiquidations(
                    servicelayer.service.ServiceWebStub.GetCompanyLiquidationsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCompanyLiquidations operation
           */
            public void receiveErrorgetCompanyLiquidations(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for forgotPassword method
            * override this method for handling normal response from forgotPassword operation
            */
           public void receiveResultforgotPassword(
                    servicelayer.service.ServiceWebStub.ForgotPasswordResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from forgotPassword operation
           */
            public void receiveErrorforgotPassword(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for insertProject method
            * override this method for handling normal response from insertProject operation
            */
           public void receiveResultinsertProject(
                    servicelayer.service.ServiceWebStub.InsertProjectResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from insertProject operation
           */
            public void receiveErrorinsertProject(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for insertBill method
            * override this method for handling normal response from insertBill operation
            */
           public void receiveResultinsertBill(
                    servicelayer.service.ServiceWebStub.InsertBillResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from insertBill operation
           */
            public void receiveErrorinsertBill(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllSalarySummaryVersion method
            * override this method for handling normal response from getAllSalarySummaryVersion operation
            */
           public void receiveResultgetAllSalarySummaryVersion(
                    servicelayer.service.ServiceWebStub.GetAllSalarySummaryVersionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllSalarySummaryVersion operation
           */
            public void receiveErrorgetAllSalarySummaryVersion(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getTypeExchange method
            * override this method for handling normal response from getTypeExchange operation
            */
           public void receiveResultgetTypeExchange(
                    servicelayer.service.ServiceWebStub.GetTypeExchangeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getTypeExchange operation
           */
            public void receiveErrorgetTypeExchange(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteUser method
            * override this method for handling normal response from deleteUser operation
            */
           public void receiveResultdeleteUser(
                    servicelayer.service.ServiceWebStub.DeleteUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteUser operation
           */
            public void receiveErrordeleteUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getProjectsLiquidationsWithMoreEarnings method
            * override this method for handling normal response from getProjectsLiquidationsWithMoreEarnings operation
            */
           public void receiveResultgetProjectsLiquidationsWithMoreEarnings(
                    servicelayer.service.ServiceWebStub.GetProjectsLiquidationsWithMoreEarningsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getProjectsLiquidationsWithMoreEarnings operation
           */
            public void receiveErrorgetProjectsLiquidationsWithMoreEarnings(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getBillsByFiltersWithCharges method
            * override this method for handling normal response from getBillsByFiltersWithCharges operation
            */
           public void receiveResultgetBillsByFiltersWithCharges(
                    servicelayer.service.ServiceWebStub.GetBillsByFiltersWithChargesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getBillsByFiltersWithCharges operation
           */
            public void receiveErrorgetBillsByFiltersWithCharges(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCategoriesByProject method
            * override this method for handling normal response from getCategoriesByProject operation
            */
           public void receiveResultgetCategoriesByProject(
                    servicelayer.service.ServiceWebStub.GetCategoriesByProjectResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCategoriesByProject operation
           */
            public void receiveErrorgetCategoriesByProject(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createLiquidation method
            * override this method for handling normal response from createLiquidation operation
            */
           public void receiveResultcreateLiquidation(
                    servicelayer.service.ServiceWebStub.CreateLiquidationResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createLiquidation operation
           */
            public void receiveErrorcreateLiquidation(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllBillsByFilters method
            * override this method for handling normal response from getAllBillsByFilters operation
            */
           public void receiveResultgetAllBillsByFilters(
                    servicelayer.service.ServiceWebStub.GetAllBillsByFiltersResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllBillsByFilters operation
           */
            public void receiveErrorgetAllBillsByFilters(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for insertCharge method
            * override this method for handling normal response from insertCharge operation
            */
           public void receiveResultinsertCharge(
                    servicelayer.service.ServiceWebStub.InsertChargeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from insertCharge operation
           */
            public void receiveErrorinsertCharge(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for changePassword method
            * override this method for handling normal response from changePassword operation
            */
           public void receiveResultchangePassword(
                    servicelayer.service.ServiceWebStub.ChangePasswordResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from changePassword operation
           */
            public void receiveErrorchangePassword(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCompanyLiquidation method
            * override this method for handling normal response from getCompanyLiquidation operation
            */
           public void receiveResultgetCompanyLiquidation(
                    servicelayer.service.ServiceWebStub.GetCompanyLiquidationResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCompanyLiquidation operation
           */
            public void receiveErrorgetCompanyLiquidation(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for resetPassword method
            * override this method for handling normal response from resetPassword operation
            */
           public void receiveResultresetPassword(
                    servicelayer.service.ServiceWebStub.ResetPasswordResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from resetPassword operation
           */
            public void receiveErrorresetPassword(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getProjectLiquidation method
            * override this method for handling normal response from getProjectLiquidation operation
            */
           public void receiveResultgetProjectLiquidation(
                    servicelayer.service.ServiceWebStub.GetProjectLiquidationResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getProjectLiquidation operation
           */
            public void receiveErrorgetProjectLiquidation(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCharges method
            * override this method for handling normal response from getCharges operation
            */
           public void receiveResultgetCharges(
                    servicelayer.service.ServiceWebStub.GetChargesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCharges operation
           */
            public void receiveErrorgetCharges(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getSalarySummaryByVersion method
            * override this method for handling normal response from getSalarySummaryByVersion operation
            */
           public void receiveResultgetSalarySummaryByVersion(
                    servicelayer.service.ServiceWebStub.GetSalarySummaryByVersionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getSalarySummaryByVersion operation
           */
            public void receiveErrorgetSalarySummaryByVersion(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteBills method
            * override this method for handling normal response from deleteBills operation
            */
           public void receiveResultdeleteBills(
                    servicelayer.service.ServiceWebStub.DeleteBillsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteBills operation
           */
            public void receiveErrordeleteBills(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateProject method
            * override this method for handling normal response from updateProject operation
            */
           public void receiveResultupdateProject(
                    servicelayer.service.ServiceWebStub.UpdateProjectResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateProject operation
           */
            public void receiveErrorupdateProject(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getEmployed method
            * override this method for handling normal response from getEmployed operation
            */
           public void receiveResultgetEmployed(
                    servicelayer.service.ServiceWebStub.GetEmployedResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getEmployed operation
           */
            public void receiveErrorgetEmployed(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCategoriesAllVersions method
            * override this method for handling normal response from getCategoriesAllVersions operation
            */
           public void receiveResultgetCategoriesAllVersions(
                    servicelayer.service.ServiceWebStub.GetCategoriesAllVersionsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCategoriesAllVersions operation
           */
            public void receiveErrorgetCategoriesAllVersions(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getDistributionTypes method
            * override this method for handling normal response from getDistributionTypes operation
            */
           public void receiveResultgetDistributionTypes(
                    servicelayer.service.ServiceWebStub.GetDistributionTypesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getDistributionTypes operation
           */
            public void receiveErrorgetDistributionTypes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateCategory method
            * override this method for handling normal response from updateCategory operation
            */
           public void receiveResultupdateCategory(
                    servicelayer.service.ServiceWebStub.UpdateCategoryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateCategory operation
           */
            public void receiveErrorupdateCategory(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getUser method
            * override this method for handling normal response from getUser operation
            */
           public void receiveResultgetUser(
                    servicelayer.service.ServiceWebStub.GetUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getUser operation
           */
            public void receiveErrorgetUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getProjectLiquidations method
            * override this method for handling normal response from getProjectLiquidations operation
            */
           public void receiveResultgetProjectLiquidations(
                    servicelayer.service.ServiceWebStub.GetProjectLiquidationsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getProjectLiquidations operation
           */
            public void receiveErrorgetProjectLiquidations(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllVersionsSalarySummary method
            * override this method for handling normal response from getAllVersionsSalarySummary operation
            */
           public void receiveResultgetAllVersionsSalarySummary(
                    servicelayer.service.ServiceWebStub.GetAllVersionsSalarySummaryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllVersionsSalarySummary operation
           */
            public void receiveErrorgetAllVersionsSalarySummary(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getChargesByBill method
            * override this method for handling normal response from getChargesByBill operation
            */
           public void receiveResultgetChargesByBill(
                    servicelayer.service.ServiceWebStub.GetChargesByBillResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getChargesByBill operation
           */
            public void receiveErrorgetChargesByBill(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getProjectsByStatus method
            * override this method for handling normal response from getProjectsByStatus operation
            */
           public void receiveResultgetProjectsByStatus(
                    servicelayer.service.ServiceWebStub.GetProjectsByStatusResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getProjectsByStatus operation
           */
            public void receiveErrorgetProjectsByStatus(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteCategory method
            * override this method for handling normal response from deleteCategory operation
            */
           public void receiveResultdeleteCategory(
                    servicelayer.service.ServiceWebStub.DeleteCategoryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteCategory operation
           */
            public void receiveErrordeleteCategory(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for estimateSalarySummary method
            * override this method for handling normal response from estimateSalarySummary operation
            */
           public void receiveResultestimateSalarySummary(
                    servicelayer.service.ServiceWebStub.EstimateSalarySummaryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from estimateSalarySummary operation
           */
            public void receiveErrorestimateSalarySummary(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteEmployed method
            * override this method for handling normal response from deleteEmployed operation
            */
           public void receiveResultdeleteEmployed(
                    servicelayer.service.ServiceWebStub.DeleteEmployedResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteEmployed operation
           */
            public void receiveErrordeleteEmployed(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteProject method
            * override this method for handling normal response from deleteProject operation
            */
           public void receiveResultdeleteProject(
                    servicelayer.service.ServiceWebStub.DeleteProjectResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteProject operation
           */
            public void receiveErrordeleteProject(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteCharges method
            * override this method for handling normal response from deleteCharges operation
            */
           public void receiveResultdeleteCharges(
                    servicelayer.service.ServiceWebStub.DeleteChargesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteCharges operation
           */
            public void receiveErrordeleteCharges(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getUsers method
            * override this method for handling normal response from getUsers operation
            */
           public void receiveResultgetUsers(
                    servicelayer.service.ServiceWebStub.GetUsersResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getUsers operation
           */
            public void receiveErrorgetUsers(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for insertEmployed method
            * override this method for handling normal response from insertEmployed operation
            */
           public void receiveResultinsertEmployed(
                    servicelayer.service.ServiceWebStub.InsertEmployedResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from insertEmployed operation
           */
            public void receiveErrorinsertEmployed(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getBillsByFilters method
            * override this method for handling normal response from getBillsByFilters operation
            */
           public void receiveResultgetBillsByFilters(
                    servicelayer.service.ServiceWebStub.GetBillsByFiltersResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getBillsByFilters operation
           */
            public void receiveErrorgetBillsByFilters(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateCharge method
            * override this method for handling normal response from updateCharge operation
            */
           public void receiveResultupdateCharge(
                    servicelayer.service.ServiceWebStub.UpdateChargeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateCharge operation
           */
            public void receiveErrorupdateCharge(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getProjectLiquidationsPreview method
            * override this method for handling normal response from getProjectLiquidationsPreview operation
            */
           public void receiveResultgetProjectLiquidationsPreview(
                    servicelayer.service.ServiceWebStub.GetProjectLiquidationsPreviewResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getProjectLiquidationsPreview operation
           */
            public void receiveErrorgetProjectLiquidationsPreview(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getChargesByFilters method
            * override this method for handling normal response from getChargesByFilters operation
            */
           public void receiveResultgetChargesByFilters(
                    servicelayer.service.ServiceWebStub.GetChargesByFiltersResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getChargesByFilters operation
           */
            public void receiveErrorgetChargesByFilters(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCategories method
            * override this method for handling normal response from getCategories operation
            */
           public void receiveResultgetCategories(
                    servicelayer.service.ServiceWebStub.GetCategoriesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCategories operation
           */
            public void receiveErrorgetCategories(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for insertCategory method
            * override this method for handling normal response from insertCategory operation
            */
           public void receiveResultinsertCategory(
                    servicelayer.service.ServiceWebStub.InsertCategoryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from insertCategory operation
           */
            public void receiveErrorinsertCategory(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for insertUser method
            * override this method for handling normal response from insertUser operation
            */
           public void receiveResultinsertUser(
                    servicelayer.service.ServiceWebStub.InsertUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from insertUser operation
           */
            public void receiveErrorinsertUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getProjectsByUserContext method
            * override this method for handling normal response from getProjectsByUserContext operation
            */
           public void receiveResultgetProjectsByUserContext(
                    servicelayer.service.ServiceWebStub.GetProjectsByUserContextResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getProjectsByUserContext operation
           */
            public void receiveErrorgetProjectsByUserContext(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for existLiquidation method
            * override this method for handling normal response from existLiquidation operation
            */
           public void receiveResultexistLiquidation(
                    servicelayer.service.ServiceWebStub.ExistLiquidationResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from existLiquidation operation
           */
            public void receiveErrorexistLiquidation(java.lang.Exception e) {
            }
                


    }
    