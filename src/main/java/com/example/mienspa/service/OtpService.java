//package com.example.mienspa.service;
//
//import java.util.Random;
//import java.util.concurrent.TimeUnit;
//import org.springframework.stereotype.Service;
//import com.google.common.cache.LoadingCache;
//import com.google.common.cache.CacheBuilder;
//import com.google.common.cache.CacheLoader;
//
//@Service
//public class OtpService {
//
//    private static final Integer EXPIRE_MINS = 7;
//    private LoadingCache<String, Integer> otpCache;
//    public OtpService(){
//      super();
//      otpCache = CacheBuilder.newBuilder().
//      expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
//      .build(new CacheLoader<String, Integer>() {
//      public Integer load(String key) {
//             return 0;
//       }
//   });
// }
//
//public int generateOTP(String key){
//Random random = new Random();
//int otp = 10000 + random.nextInt(90000);
//otpCache.put(key, otp);
//return otp;
// }
// 
// public int getOtp(String key){ 
//try{
// return otpCache.get(key); 
//}catch (Exception e){
// return 0; 
//}
// }
//
//public void clearOTP(String key){ 
// otpCache.invalidate(key);
// }
//}