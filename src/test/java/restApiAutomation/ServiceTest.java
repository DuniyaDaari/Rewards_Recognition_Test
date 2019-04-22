package restApiAutomation;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class ServiceTest {

    @Test
    public void testGetEmployeesByTeam() throws Exception {
        int teamId = 6;
        int expectedEmployeeCount = 6;
        Response response = ServiceCalls.testGetEmployeesByTeam(teamId);
        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.getBody().jsonPath().getList("employees").size(), expectedEmployeeCount);
    }

    @Test
    public void testGetVoucherByName() throws Exception {
        String voucherName = "Amazon100";
        int voucherExpectedAmount = 100;
        Response response = ServiceCalls.testGetVoucherByName(voucherName);
        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.getBody().jsonPath().getInt("voucherAmount"), voucherExpectedAmount);
    }

    @Test
    public void testGetVouchersByAmount() throws Exception {
        int voucherAmount = 100;
        List<String> expectedVoucherList = Arrays.asList("Amazon100");
        Response response = ServiceCalls.testGetVoucherByAmount(voucherAmount);
        System.out.println(response.getBody().asString());
        List<String> actualVoucherList = response.getBody().jsonPath().getList("voucherName");
        for (int i = 0; i < expectedVoucherList.size(); i++) {
            Assert.assertTrue(actualVoucherList.contains(expectedVoucherList.get(i)));
        }
    }

    @Test
    public void testGetLineManagerTeams() throws Exception {
        String lineManagerPid = "P1006";
        List<String> expectedTeamNames = Arrays.asList("Karthikey");
        Response response = ServiceCalls.testGetLineManagerTeams(lineManagerPid);
        System.out.println(response.getBody().asString());
        List<String> actualTeamNames = response.getBody().jsonPath().getList("name");
        for (int i = 0; i < expectedTeamNames.size(); i++) {
            Assert.assertTrue(actualTeamNames.contains(expectedTeamNames.get(i)));
        }
    }

    @Test
    public void testGetTeamDetails() throws Exception {
        int teamId = 2;
        String expectedTeamName = "Karthikey";
        List<String> expectedEmployeeFirstNameList = Arrays.asList("Hrishikesh", "Kamlesh", "Viresh", "Lokesh", "Second", "Ganesh");
        Response response = ServiceCalls.testGetEmployeesByTeam(teamId);
        System.out.println(response.getBody().asString());
        String actualTeamName = response.getBody().jsonPath().get("name");
        System.out.println(actualTeamName);
        List<String> actualEmployeeFirstNameList = response.getBody().jsonPath().getList("employees.firstName");
        System.out.println(actualEmployeeFirstNameList);
        Assert.assertEquals(actualTeamName.toString(), expectedTeamName.toString());

        for (String actualEmployeeFirstName : actualEmployeeFirstNameList) {
            Assert.assertTrue(expectedEmployeeFirstNameList.contains(actualEmployeeFirstName));
        }
    }

    @Test
    public void testGetAllRewards() throws Exception {

        List<String> expectedRewardNameList = Arrays.asList("You Carried My Day", "Pat On The Back");
        Response response = ServiceCalls.testGetAllRewards();
        System.out.println(response.getBody().asString());
        List<String> actualRewardNameList = response.getBody().jsonPath().getList("rewardName");
        for (int i = 0; i < expectedRewardNameList.size(); i++) {
            Assert.assertTrue(actualRewardNameList.contains(expectedRewardNameList.get(i)));
        }
    }

    @Test
    public void testGetRewardsByEmployeeId() throws Exception {
        String employeeId = "P1001";
        List<Integer> expectedRewardIdList = Arrays.asList(1);
        Response response = ServiceCalls.testGetRewardsByEmployeeId(employeeId);
        System.out.println(response.getBody().asString());
        List<Integer> actualRewardIdList = response.getBody().jsonPath().getList("rewardId");
        for (int i = 0; i < expectedRewardIdList.size(); i++) {
            Assert.assertTrue(actualRewardIdList.contains(expectedRewardIdList.get(i)));
        }
    }

    @Test
    public void testSaveReward() throws Exception {
        JSONObject rewardsJsonObject = new JSONObject();
        rewardsJsonObject.put("rewardName", "You Carried My Day").put("comment", "Award given for your good work")
                .put("rewardID", 1).put("teamId", 1).put("employeeId", "P1002").put("nominatedBy", "P1000");
        Response response = ServiceCalls.testSaveReward(rewardsJsonObject);
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println(response.getBody().asString());
    }

    @Test
    public void testUpdateReward() throws Exception {
        JSONObject rewardsJsonObject = new JSONObject();
        rewardsJsonObject.put("rewardName", "You Carried My Day").put("comment", "Award given for your good work")
                .put("rewardId", 2).put("teamId", 1).put("employeeId", "P1002").put("nominatedBy", "P1000");
        Response response = ServiceCalls.testUpdateReward(rewardsJsonObject);
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println(response.getBody().asString());
    }
}
