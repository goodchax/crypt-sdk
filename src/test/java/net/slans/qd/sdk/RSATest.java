package net.slans.qd.sdk;

import net.slans.qd.sdk.pay.SlansApiException;
import net.slans.qd.sdk.pay.SlansConstants;
import net.slans.qd.sdk.pay.util.AsymmetricManager;
import org.junit.Test;

public class RSATest {

    @Test
    public void sign() throws SlansApiException {

        String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCPmeP1t5qJi0ljeo5f5GtThlf1Xw7EEQwGNrKlYHAxnYWaxnk4/eNXLo5mX+go9avxJT4ZElGmIT3X6XspS5dOAq+EtG2jOUx60Ki+E44LS+BOw5rquB9IukgOhd8LRbQzIRm2bUzKicAQ6mHNadRdyfJD9p51kNHm3DdliJutUFZWNXmJMHV2LLQ1zFjMfUphN+q8QYjE1E0MA6FFBUTtkgPwr4EPCnQQP1TuJFLqXcnpBV+TJ9hrtl70qf24IWmXxFsUfV7xrvtLV2SvQf+btWjBO3Guu+SkZhthWuoU8A71xVQWHAvTGhbbJEtrrXqx/aqLtncHE7iwt0gbH/N5AgMBAAECggEAEmqlHr1GV3+CkPyFx+yOiy6+lxxy01hwV1eqf6UnnmVaXig4IcQF5Qd2gdCpAJ/JRmsFVMfRquG2gbtS7U/hfsK/aeCN8xIFKfcavQ3a+L3pbZCOjK3CbVxeo2Yt3c85thj3qDrKT2ttM34rvuFkuAaPSIIXfnJtSRNTE3V6qbZjvgqot3IitgcPO3rQA+Mz16jqD5jp1eJpKdoL+A1Tf4uk+rsSneFkbj6TRHgSDyKQnMmP6xteyTqhP1Gm9fKgOIgTgDFbiQvFwGPvx2/JtHQXLlnpUg932VVCF66e2nLWh9FeF0ZX54f59n85FDo6nAo9e/w2j/oHAPADHy56gQKBgQDAriHOTShiEoB33i53buu1w1FI0BTYymM07jh17EpSSctBubbFGZ+cF87Xl5TNkBH1KpK6WFYGOOExKZ348KUtxgKI2al2pkiPCFMYdmDnvMEIKtWhYReEY+HTMUvItGXmuci6XtIakYiHyiiog0VSP40BK21SKha7OzX94BL8KQKBgQC+ytE3fZy8m/8qQ6KGfN94U1EH8QSm/9NKHtpvLHc5CEDRjd3rqEwaGyGG1vYmA0Iu+v4+YgpyJWNXtHsHn8AHNqfW6YJRi5inhzjNYinDCbGIcZIzecB2CRw5atoWKpucOqZQx6Gvxm4my0JARVsBTNwCpE7K4mRN7iYgjuIm0QKBgBx8YgEXBSZip92Q2nvLyUtJAgZqxPo8Cai0o2JDucUqpcMkyrGfFSrFlk3nWhd9+jmtvp2QG7+1vw0+ot0/7puyz4Md8uDYupdy2p4eOjtoe31Sn9zI9uoavjMz8YlNqlcPFm7/P76vz2iL6bDoH2KGhLksi2//SvcJTPUooIXpAoGBAJs/D1SW2oXCbbOnS0UhsPkI2oOaw41b10KVrmoOBYkbxRp+1Jgf3qTTdMlyeuJdfmoaA+0g+5z7/UTtb7q5a5izCYlfgpH2BCY1aqv8dzzj01jSCQPnywqAyT2TS5gD3ZEXTCOo/VC5JwJcHDGN/comD1P6/L4rzzB2WwHdIfoxAoGAf4vuetH7fBtq4GbmxKmF5dAWj5knIQqo0rw3YPK7P3SpF7hkL/uxQwFBav+Bk/XrbDN6nsirjuqJP6sEl659tE3S8nOOS/GucLwX5IbYD/sPOZCjx5kRF1ByRlDRC3vgFo9MmGM4+5uDWmV6d11qE7ddsgcCwZ69F3HW0XvW1NA=";
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj5nj9beaiYtJY3qOX+RrU4ZX9V8OxBEMBjaypWBwMZ2FmsZ5OP3jVy6OZl/oKPWr8SU+GRJRpiE91+l7KUuXTgKvhLRtozlMetCovhOOC0vgTsOa6rgfSLpIDoXfC0W0MyEZtm1MyonAEOphzWnUXcnyQ/aedZDR5tw3ZYibrVBWVjV5iTB1diy0NcxYzH1KYTfqvEGIxNRNDAOhRQVE7ZID8K+BDwp0ED9U7iRS6l3J6QVfkyfYa7Ze9Kn9uCFpl8RbFH1e8a77S1dkr0H/m7VowTtxrrvkpGYbYVrqFPAO9cVUFhwL0xoW2yRLa616sf2qi7Z3BxO4sLdIGx/zeQIDAQAB";

//        String sign = SlansSignature.encrypt("123456", publicKey, "UTF-8", SlansConstants.SIGN_TYPE_RSA);
        String sign = AsymmetricManager.getByName(SlansConstants.SIGN_TYPE_RSA2).encrypt("123456", "UTF-8", publicKey);

        System.out.println(sign);


        String planText = AsymmetricManager.getByName(SlansConstants.SIGN_TYPE_RSA2).decrypt(sign, "UTF-8", privateKey);
                System.out.println(planText);

    }


    @Test
    public void verify() throws SlansApiException {
//        String planText = SlansSignature.verify("b/YrCQb0wh97nXEnm09GlOd7GhfBWb5fWs7Eznfq1XVhEW87tOWV+KyAbj5LNeT+R/dgg/Yd1cPH3HdMUQ5rSQbT5IoTnAHhcDIbD1lRjUKm0v1bshS4AWbYoVxEDAvw/gJB/yBa8WG3o0pepT60y9mFWdXcJ/RavENj7MCve1ufFmS6m7Zcm4s3VcjwZelK6R4hAe76EYf3tCZAnfxXttCB0+rZizHnRWJH2tkleT5JZoAtJ2QbGOJZqc2D866TWkbk5QrV6+ka49KvzCrR4AnbG66EWjSAV5apzzxgmM4duwlk3xfB54Eym/lAGn+Ycl2Txh/tuIKFpZVjygzy9w==", publicKey, "UTF-8", SlansConstants.SIGN_TYPE_RSA);
//        System.out.println(publicKey);

    }
}
