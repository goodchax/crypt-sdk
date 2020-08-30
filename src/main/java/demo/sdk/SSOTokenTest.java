package demo.sdk;

import net.slans.sdk.*;
import net.slans.sdk.request.SSOTokenRequest;

public class SSOTokenTest {

    public static final String API_URL = "http://teoa.qidianren.com";
    public static final String CORPID = "slans1f0f47f2ba6e4771ad510573ea68feea";
    public static final String SSO_SECRET = "2274128203b34612b6222f683fe93d98";

    public static final String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCb12f4QeIDWwA/1e5KLBRdzoUcUvhrMJHNBi17jnnKUzmJH7+ny/l53sGCeJF3SnFrSn6Y/1mKdLnVt0ZdVVPCehsfcmnF4Kb/6AUuo4sMAn0oxewDmLVP9TLWPJvkVVNDbgUET9mI+m91bYJYs+t20VQFPYb8RzDHqi/g86U+ahSFWjW5h1k8znQ9BkLgQzlvh3Qs80r5VRCSeJvDtd+klXxZM53NcTqgoqLvXQ24rh8FdYfM23JumPUZVN8L6O4narB19PhMVCo8tPCjWB95wwWmA1nTtn07G6GoSa5/1W/OToAN8rbk6A+aWIbmbX7FADi4OSCIFmCiWZT7tcpzAgMBAAECggEALsdB1iswhIFUWdUFag5BLsdG/XGKT+KMYVZxYJ0rwaeppZd6GAwKHqLWwysdvwkBcosx5QsA/aZ8AcK5xFpQghfU/GODjHoapsYmiBFfM4OrvglrBslX42BemiAnSyqxi5QCvYCac5t4yW7NqYKYM1oyNkB5vFBCPCzBomyANXN3ilJKvOYZbpr2IakL0oT9G0ocN05OS+HCj3hqupKdg56FjipMuykVpjIp0c6PwJiNzBxYviNDnP4O0m98R7awLpmyuo0mDsaMVXcFVvPJRx5czM/jVNrDWNaI4Pbagi1/my7TBucEuMcZMPPUQMnypoaLvKjvQPANdd4bUV4jQQKBgQD2zv8EN5qWwoMS2J1M+MSN67BUAxRo1Jk8VWW7yVzctfJu8ED1AaJaNYqFLFytXLHkBnWwwYswoPf1GO5hmDxAvPdBHwp26zj5ULX5LNfFdDk2sjzPwZZqWZ/oh2Wv7PPzLSfLrIX49EgjSpBFuh7h0ZSUTj1QiqbbucZN/4ogdQKBgQChpSeYKvv7J64d3tnYf+W9uuxetrHwsKc8bxxR78Nodbc74owTb2OisSkwjbGL/cOd8QVmDi0ma28VO6jSueiKXe7+OT89qJFqpjNL0PS9nVTe8Yc9fXwaHxciE0GKsFfVbJjvBcifIHbhEUCjmToPJoWJGJ1wtRcMe7yXrNJiRwKBgQCNSmb3dRWMpQIAcf1rKSbs0DZ7gd/XO44V2hWJcXc16vtEw0mMeUkGN4x7PLA5ls1H6uLJM6OJTT1mFMWzHUGxFfy+TRTiizpsj+X9JczIfwaZ52Ok1ABWFcTTi1+NXSsy1XDoynVP1hjOC0uYfzpasG5MkJXNW/K16x45XMcoWQKBgCvth+8Qb133kJfJggYV68c9zxKGL6ErQKdpwpZ5w+7VXrBFhq+Za8hAwOLS/tb+ZXS8lS0A2UBBnfR1PBHYN9uyX0pzID6PWsVS2UnXRUtqQDA+DvSk717h6BV4ii04cwYq9vyt47FqzyOm3pwW/fI0E8IIEHEapWLdFYlai+ntAoGBAJcX3MWFGPwMoNaQM5F4RjCuDUvHBwMMZLeN3KtNaDRtLlp2+v9+ouHk14+Jiek2BS9d6kqR5wHcHFHotkmqnerVCVrwQvej3XMC8UyYTHpQctyOZ6YcOjniOFuJE7CQnkfikkn3syR7FvSq7k5QKvFH1KP1nom4r3eq2/UZE/3s";
    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm9dn+EHiA1sAP9XuSiwUXc6FHFL4azCRzQYte455ylM5iR+/p8v5ed7BgniRd0pxa0p+mP9ZinS51bdGXVVTwnobH3JpxeCm/+gFLqOLDAJ9KMXsA5i1T/Uy1jyb5FVTQ24FBE/ZiPpvdW2CWLPrdtFUBT2G/Ecwx6ov4POlPmoUhVo1uYdZPM50PQZC4EM5b4d0LPNK+VUQknibw7XfpJV8WTOdzXE6oKKi710NuK4fBXWHzNtybpj1GVTfC+juJ2qwdfT4TFQqPLTwo1gfecMFpgNZ07Z9OxuhqEmuf9Vvzk6ADfK25OgPmliG5m1+xQA4uDkgiBZgolmU+7XKcwIDAQAB";

    public static void main(String[] args) throws SlansApiException {


        SSOTokenRequest request = new SSOTokenRequest();
        request.setCorpid(CORPID);
        request.setSsosecret(SSO_SECRET);

        SlansClient client = new DefaultSlansClient(API_URL, CORPID, null);
        client.setHttpMethod(SlansConstants.METHOD_GET);
        SlansResponse rsp = client.execute(request);
        System.out.println("rsp" + rsp.getBody());
        if (rsp.isSuccess()) {

        } else {

        }
    }

}
