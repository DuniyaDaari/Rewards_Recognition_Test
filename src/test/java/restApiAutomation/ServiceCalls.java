package restApiAutomation;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

import static restApiAutomation.Constants.REQUESTSPECIFICATION;
import static restApiAutomation.Constants.URL;

public class ServiceCalls {
    public static Response testGetEmployeesByTeam(int teamId) throws Exception {
        return RestAssured.given().spec(REQUESTSPECIFICATION).when().get(URL +  "team/" + teamId) ;
    }

    public static Response testGetVoucherByName(String voucherName) throws Exception {
        return RestAssured.given().spec(REQUESTSPECIFICATION).when().get(URL + "voucher/" + voucherName);
    }

    public static Response testGetVoucherByAmount(int voucherAmount) throws Exception {
        return RestAssured.given().spec(REQUESTSPECIFICATION).when().get(URL + "vouchers/" + voucherAmount);
    }
    public static Response testGetLineManagerTeams(String lineManagerPid) throws Exception {
        return RestAssured.given().spec(REQUESTSPECIFICATION).when().get(URL + "teams/" + lineManagerPid);
    }
    public static Response testGetAllRewards() throws Exception {
        return RestAssured.given().spec(REQUESTSPECIFICATION).when().get(URL + "rewards");
    }
    public static Response testGetRewardsByEmployeeId(String employeeId) throws Exception {
        return RestAssured.given().spec(REQUESTSPECIFICATION).when().get(URL + "employee" + employeeId + "rewards");
    }
    public static Response testSaveReward(JSONObject jsonObject) throws Exception {
        return RestAssured.given().spec(REQUESTSPECIFICATION).body(jsonObject.toString()).when().post(URL + "employee/ " + jsonObject.get("employeeId") + "/reward");
    }
    public static Response testUpdateReward(JSONObject jsonObject) throws Exception {
        return RestAssured.given().spec(REQUESTSPECIFICATION).body(jsonObject.toString()).when().put(URL + "employee/ " + jsonObject.get("employeeId") + "/reward" +jsonObject.get("rewardId"));
    }

}
