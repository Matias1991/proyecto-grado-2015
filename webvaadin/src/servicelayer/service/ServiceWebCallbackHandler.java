
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
                


    }
    