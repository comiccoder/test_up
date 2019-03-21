import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class main01 {

    public static void main(String[] args)
    {
        Node head=new Node();
        if(head.next==null)
            head.next=new Node();


        String s = "{\"name\":\"peter\"}";
        JSONObject object = JSON.parseObject(s);
        System.out.println(object.get("name"));
    }
}
