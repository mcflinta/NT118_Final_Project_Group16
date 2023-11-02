package com.ixxc.uiot.API;

import com.ixxc.uiot.GlobalVars;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static String UserToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoREkwZ2hyVlJvaE5zVy1wSXpZeDBpT2lHMzNlWjJxV21sRk4wWGE1dWkwIn0.eyJleHAiOjE2OTg5ODM3MTIsImlhdCI6MTY5ODg5NzMxMiwianRpIjoiNWIzNmFjYTgtZjc3MS00YTY1LTk3ZDctYzBlOTNhYTI3YWNkIiwiaXNzIjoiaHR0cHM6Ly91aW90Lml4eGMuZGV2L2F1dGgvcmVhbG1zL21hc3RlciIsImF1ZCI6WyJzdHJpbmctcmVhbG0iLCJtYXN0ZXItcmVhbG0iLCJhY2NvdW50Il0sInN1YiI6IjFiYjBlMWRjLTcyNjAtNGU3MC1hOTFiLTI0YTM0YWU2MGZmZSIsInR5cCI6IkJlYXJlciIsImF6cCI6Im9wZW5yZW1vdGUiLCJzZXNzaW9uX3N0YXRlIjoiMGRjMWI2YzktNDA2Ni00NTQ3LWE3YzYtYjQ5YmRkZjFjYzRmIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwczovL3Vpb3QuaXh4Yy5kZXYiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImNyZWF0ZS1yZWFsbSIsImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsic3RyaW5nLXJlYWxtIjp7InJvbGVzIjpbInZpZXctaWRlbnRpdHktcHJvdmlkZXJzIiwidmlldy1yZWFsbSIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInF1ZXJ5LXJlYWxtcyIsInZpZXctYXV0aG9yaXphdGlvbiIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS11c2VycyIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIiwicXVlcnktZ3JvdXBzIl19LCJvcGVucmVtb3RlIjp7InJvbGVzIjpbIndyaXRlOmxvZ3MiLCJyZWFkIiwid3JpdGU6YXNzZXRzIiwid3JpdGU6YWRtaW4iLCJyZWFkOmxvZ3MiLCJyZWFkOm1hcCIsInJlYWQ6YXNzZXRzIiwid3JpdGU6dXNlciIsInJlYWQ6dXNlcnMiLCJ3cml0ZTpydWxlcyIsInJlYWQ6cnVsZXMiLCJyZWFkOmluc2lnaHRzIiwid3JpdGU6YXR0cmlidXRlcyIsIndyaXRlIiwid3JpdGU6aW5zaWdodHMiLCJyZWFkOmFkbWluIl19LCJtYXN0ZXItcmVhbG0iOnsicm9sZXMiOlsidmlldy1pZGVudGl0eS1wcm92aWRlcnMiLCJ2aWV3LXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwicXVlcnktcmVhbG1zIiwidmlldy1hdXRob3JpemF0aW9uIiwicXVlcnktY2xpZW50cyIsInF1ZXJ5LXVzZXJzIiwibWFuYWdlLWV2ZW50cyIsIm1hbmFnZS1yZWFsbSIsInZpZXctZXZlbnRzIiwidmlldy11c2VycyIsInZpZXctY2xpZW50cyIsIm1hbmFnZS1hdXRob3JpemF0aW9uIiwibWFuYWdlLWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsInNpZCI6IjBkYzFiNmM5LTQwNjYtNDU0Ny1hN2M2LWI0OWJkZGYxY2M0ZiIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6Im1haSBjdW9uZyIsInByZWZlcnJlZF91c2VybmFtZSI6Im1haWN1b25nIiwiZ2l2ZW5fbmFtZSI6Im1haSIsImZhbWlseV9uYW1lIjoiY3VvbmciLCJlbWFpbCI6Im1haWN1b25nODcwOUBnbWFpbC5jb20ifQ.pygdPSpxJvHbRHeCLDrQIThYKhqNrCnchBuzg3Zj1PmEx8Q6RCx43jkHXwSMYHzwlme0bjYJrjws6tSX9UyP1gbwDWNOoQON-Qi5NLvvYKWFnYtnbRpbkBieuz7_FYqgBZADppUMpwVzzhbNxwbayqblORuBneh9MQHmECYmT7Vl5b3A09agZ0r2x1zmRW0uziq4Q8sTlx4GEMBhzQatE85SEeVHdfHQXarzYTNQsLj5k8lpZ-SJRuMqgLazAfRX1kcq0yi30UHTddzsevTtHIQph29oswRJ-axv4D1mPY9MeQe5Xet968A6ohWx16eSOLPgsGWWDlTbU2ZnLIqXVA";
    //public static String UserToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoREkwZ2hyVlJvaE5zVy1wSXpZeDBpT2lHMzNlWjJxV21sRk4wWGE1dWkwIn0.eyJleHAiOjE2OTg3MjI1MTcsImlhdCI6MTY5ODYzNjExNywianRpIjoiNWZkNjUxMWMtZmY3Zi00OWVhLTg2OTMtMWM2MWE2N2VmMjExIiwiaXNzIjoiaHR0cHM6Ly91aW90Lml4eGMuZGV2L2F1dGgvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI0ZTNhNDQ5Ni0yZjE5LTQ4MTMtYmYwMC0wOTQwN2QxZWU4Y2IiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJvcGVucmVtb3RlIiwic2Vzc2lvbl9zdGF0ZSI6Ijg2ZTMwODkzLTFkYWItNGNlNi1hZTdmLTY1ODE1NWJmOTAwOCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly91aW90Lml4eGMuZGV2Il0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLW1hc3RlciIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJvcGVucmVtb3RlIjp7InJvbGVzIjpbInJlYWQ6bWFwIiwicmVhZDpydWxlcyIsInJlYWQ6aW5zaWdodHMiLCJyZWFkOmFzc2V0cyJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwic2lkIjoiODZlMzA4OTMtMWRhYi00Y2U2LWFlN2YtNjU4MTU1YmY5MDA4IiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoiVXNlciBUZXN0IiwicHJlZmVycmVkX3VzZXJuYW1lIjoidXNlciIsImdpdmVuX25hbWUiOiJVc2VyIiwiZmFtaWx5X25hbWUiOiJUZXN0IiwiZW1haWwiOiJ1c2VyQGl4eGMuZGV2In0.VdIFc_Yp3ArDfeafZnfX4tGYB50Ni_ZjI1Zx3JZE_Er2wtp20_PPUMqHnoQl2v9AKg1MFxl_3B3dD9P6JCOUaD6PRDDHA9k-adcsIT0Ci8qoR3q1s5-M0W6207My8QlPNSOduxoXLk5RKfYsgevD2vJ2SHING7pTE5ArAkqeO2j0IjwasDoEimvsJzDlCBh78TYHJTzYfGexyjOHFSpq0-zoeHki-88SgYQkCwGJX-W5zAimlChEthoQO0yGUXpbKMwpuV4uU27DaQjFO--8M6_qSkQWznlwu-Zsdr_7-X1j_ZaiJq_Pkg_4D1GPoWBhK-YcjRC-rA0A7Bd70505Ag";
    //public static String UserToken = "..C----gKeiZAYd0V7b5VWAVhCHzwpLRZONoiPXwWKRCPgNbZ1g3T8JG1BAavCOevgJelYB_q2f8OEpfRS8YUtwmOz9SAoaYDih_22AVu-TeqlcUA3zNQDIfJ_6Zo1ovZwlr1s3VFUPFER4QyNvrOROeQ0iiLrowXWdB3g_RQ";
    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            //Log
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);

            //Bear token
            builder.addInterceptor(chain -> {
                Request newRequest = chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer " + UserToken)
                        .build();

                return chain.proceed(newRequest);
            });

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Retrofit getClient() {
        OkHttpClient client = getUnsafeOkHttpClient();
        return new Retrofit.Builder()
                .baseUrl(GlobalVars.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
