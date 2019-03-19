import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @Description: TODO
 * @Auther: liaoyl
 * @Date: 2019/3/19 0019 20:12
 */
public class EmployeeCRUDApp
{
    private final static  String ES_INDEX_COMPANY = "company";
    private final static  String ES_TYPE_Employee = "employee";
    public static void main(String[] args) throws  Exception

    {

        Settings setting = Settings.builder()
                .put("cluster.name","elasticsearch")
                .build();

        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));


        //createEmployee(client);

        //updateEmployee(client);

        //getEmployee(client);

        deleteEmployee(client);


        client.close();

    }

    private static void deleteEmployee(TransportClient client)
    {
        DeleteResponse deleteResponse = client.prepareDelete(ES_INDEX_COMPANY, ES_TYPE_Employee, "1")
                .setId("1").get();
    }

    private static void getEmployee(TransportClient client)
    {
        GetResponse getResponse = client.prepareGet(ES_INDEX_COMPANY, ES_TYPE_Employee, "1")
                .get();
        System.out.println(getResponse.getSource().toString());
    }

    private static void updateEmployee(TransportClient client) throws IOException
    {
        UpdateResponse updateResponse = client.prepareUpdate("company", "employee", "1")
                .setDoc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("name", "michael")
                        .endObject())
                .get();
        System.out.println(updateResponse.getGetResult());
    }

    private static void createEmployee(TransportClient client) throws IOException
    {
        IndexResponse response = client.prepareIndex("company", "employee", "1")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("name", "kobe")
                        .field("age", 27)
                        .field("position", "technique")
                        .field("country", "china")
                        .field("join_date", "2017-10-01")
                        .field("salary", 10000)
                        .endObject()).get();

        System.out.println(response.getResult());

    }
}
