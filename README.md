# Spring Security (v6.2) [Documentation in progress]

In this repository (`SpringSecurity6.2WithJWT`), you will find the implementation and configuration of security in Spring Security version 6.2, as well as the construction of the filters corresponding to authentication and role-based authorization using the JWT standard. Please note that this project is built with Java 17 and Spring Boot 3.2.

## Project Structure

**Directory:** `src/main/java/com.securityjwt`

**File System:**

- **controllers**
- **models**
- **repositories**
- **security**
- **services**
- *SpringSecurityJwtApplication.java* (Main.java)

The application is built on a simple business model but sufficient to integrate the security offered by Spring Security, thus focusing on the topic at hand without addressing general concepts of REST API.

In the **security** and **services** packages, you will find the programming logic directly related to the implementation of security. Understand the class tree like this:

**Directory:** `/security`

### SecurityConfig.java

##### It seems that your message is empty. Could you please provide the text you would like me to translate? The configuration used does NOT make use of the `and()` method, which is deprecated and will be removed in future versions of Spring Security; instead, it uses Lambda expressions.

This class encompasses all the configurations and filters responsible for handling authentication and authorization with Spring Security. In it, you will find the three most common and important methods of the entire setup:

#### Methods:

- `securityFilterChain()`
- `passwordEncoder()`
- `authenticationManager()`

#### securityFilterChain():

In short, `securityFilterChain()` is a conductor for the security of your web application. It is responsible for:

- Identify which requests need to be protected.
- Select the appropriate security filters for each request.
- Execute those filters in the correct order.

It's as if `securityFilterChain()` reviews each request that comes to your application and decides what security measures need to be applied.

**Code syntax:**
```
@Bean
SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
    jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
    jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        return httpSecurity
                .csrf(config -> {
                    config.disable();
                })
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/user/createUser").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
}
```

*Benefits:*

- Greater flexibility: You can set up different filters for different types of requests.
- Better performance: Only the filters that are truly needed are executed.
- More security: It helps you protect your application from common attacks.

#### passwordEncoder():

`passwordEncoder()` is a security guard for the passwords of your application. It is responsible for:

- Encode the passwords before storing them in the database.
- Verify if the passwords entered by the users are correct.

**Code syntax:**

```
@Bean
PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

*Benefits:*

- Greater security: Encrypted passwords are harder to crack.
- Protection against brute force attacks: It is harder to guess an encoded password.
- Ease of use: You don't need to worry about how to manually code passwords.

#### authenticationManager():

`authenticationManager()` is a detective that verifies if users are who they say they are. Among its functions, we have:

- Receive the users' credentials (such as username and password).
- Validate the credentials against a user store. (como una base de datos).
- Return an authentication object if the credentials are valid.

**Code syntax:**

```
@Bean
public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder =
        http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
}
```

*Benefits:*

- Greater security: It helps you ensure that only authorized users can access your application.
- Ease of use: You don't need to worry about how to manually verify credentials.
- Flexibility: You can use different types of user storage.
