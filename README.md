# Spring Security (v6.2) [Documentación en construcción]

En este repositorio (`SpringSecurity6.2WithJWT`) encontrarás la implementación y configuración de seguridad de Spring Security en su versión 6.2, así como la construcción de los filtros correspondientes a la autenticación y autorización por roles mediante el estándar JWT. Por favor considera que este proyecto está construido con Java 17 y Spring Boot 3.2.

## Estructura del proyecto

**Directorio:** `src/main/java/com.securityjwt`

**File System:**

- **controllers**
- **models**
- **repositories**
- **security**
- **services**
- *SpringSecurityJwtApplication.java* (Main.java)

La aplicación está construida bajo un modelo de negocio sencillo pero suficiente para integrar la seguridad que ofrece Spring Security, enfocándose así en el tema en cuestión sin abordar conceptos generales de API Rest.

En los paquetes **security** y **services** podrás encontrar la lógica de programación directamente relacionada con la implementación de seguridad. Entiende el árbol de clases así:

**Directorio:** `/security>`

### SecurityConfig.java

##### La configuración empleada NO hace uso del método `and()`, el cual está deprecated y será eliminado en futuras versiones de Spring Security, en su lugar hace uso de expresiones Lambda.

Esta clase acoje todas las configuraciones y filtros que se encargan de manejar la autenticación y autorización con Spring Security. En ella podrás encontrar los 3 métodos más comunes e importantes de toda la configuración:

#### Métodos:

- `securityFilterChain()`
- `passwordEncoder()`
- `authenticationManager()`

#### securityFilterChain():

En pocas palabras, `securityFilterChain()` es un director de orquesta para la seguridad de tu aplicación web. Se encarga de:

- Identificar qué peticiones necesitan ser protegidas.
- Seleccionar los filtros de seguridad adecuados para cada petición.
- Ejecutar esos filtros en el orden correcto.

Es como si `securityFilterChain()` revisara cada petición que llega a tu aplicación y decidiera qué medidas de seguridad hay que aplicar.

**Sintaxis de código:**
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

*Beneficios:*

- Mayor flexibilidad: Puedes configurar diferentes filtros para diferentes tipos de peticiones.
- Mejor rendimiento: Solo se ejecutan los filtros que realmente se necesitan.
- Más seguridad: Te ayuda a proteger tu aplicación de ataques comunes.

#### passwordEncoder():

`passwordEncoder()` es un guardia de seguridad para las contraseñas de tu aplicación. Se encarga de:

- Codificar las contraseñas antes de almacenarlas en la base de datos.
- Verificar si las contraseñas introducidas por los usuarios son correctas.

**Sintaxis de código:**

```
@Bean
PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

*Beneficios:*

- Mayor seguridad: Las contraseñas codificadas son más difíciles de descifrar.
- Protección contra ataques de fuerza bruta: Es más difícil adivinar una contraseña codificada.
- Facilidad de uso: No necesitas preocuparte por cómo codificar las contraseñas manualmente.

#### authenticationManager():

`authenticationManager()` es un detective que verifica si los usuarios son quienes dicen ser. Entre sus funciones tenemos:

- Recibir las credenciales de los usuarios (como nombre de usuario y contraseña).
- Validar las credenciales contra un almacén de usuarios (como una base de datos).
- Devolver un objeto de autenticación si las credenciales son válidas.

**Sintaxis de código:**

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

*Beneficios:*

- Mayor seguridad: Te ayuda a asegurarte de que solo los usuarios autorizados accedan a tu aplicación.
- Facilidad de uso: No necesitas preocuparte por cómo verificar las credenciales manualmente.
- Flexibilidad: Puedes usar diferentes tipos de almacenes de usuarios.
