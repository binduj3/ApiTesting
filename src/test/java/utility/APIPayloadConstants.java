package utility;

import org.json.JSONObject;

public class APIPayloadConstants {

    public static String getCreateUpdatePayload(String firstname,
                                                String lastname,
                                                int totalprice,
                                                boolean depositpaid,
                                                String checkin,
                                                String checkout,
                                                String additionalneeds){
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", checkin);
        bookingDates.put("checkout", checkout);

        JSONObject obj = new JSONObject();
        obj.put("firstname",firstname);
        obj.put("lastname",lastname);
        obj.put("totalprice",totalprice);
        obj.put("depositpaid",depositpaid);
        obj.put("bookingdates", bookingDates);
        obj.put("additionalneeds",additionalneeds);
        return obj.toString();
    }

}
