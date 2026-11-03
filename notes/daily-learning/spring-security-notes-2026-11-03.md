# Backend Engineering Learning Notes

## November 3, 2026 --- Spring Security: Authentication Context and JWT Flow

**Module:** `authentication/spring-security-authentication`\
**Session duration:** 15 minutes\
**Focus:** Understanding what happens after credentials are
authenticated and how JWT authentication works on later requests.

------------------------------------------------------------------------

## 1. Review: AuthenticationManager

`AuthenticationManager` coordinates the authentication process.

In our login flow, the application creates an unauthenticated
authentication request using the supplied username and password:

``` java
Authentication authentication =
        new UsernamePasswordAuthenticationToken(username, password);

Authentication result =
        authenticationManager.authenticate(authentication);
```

If the credentials are valid, `authenticate(...)` returns an
authenticated `Authentication` object. If they are invalid,
authentication fails with an authentication exception.

**Remember:** `AuthenticationManager` verifies credentials through the
configured authentication provider. It does not automatically generate a
JWT.

## 2. JWT generation

After successful login, the application can generate a JWT and return it
to the client.

A dedicated service such as `JwtService` can be responsible for JWT
operations, including:

-   Generating and signing a token.
-   Validating a token.
-   Extracting claims or information from a token.

The application must explicitly call the JWT service. Spring Security
does not automatically issue a JWT just because
`AuthenticationManager.authenticate(...)` succeeded.

**Important distinction:**

-   `AuthenticationManager` --- coordinates credential authentication.
-   `JwtService` --- handles token generation and token-related
    operations.

## 3. SecurityContext

Spring Security uses a `SecurityContext` to hold the current request's
authentication information.

The `SecurityContext` contains an `Authentication` object. That object
can represent the current principal and whether authentication has
succeeded.

For example:

``` java
Authentication authentication =
        SecurityContextHolder.getContext().getAuthentication();

String username = authentication.getName();
```

This retrieves the current authentication from the security context and
reads the authenticated name.

## 4. SecurityContextHolder

`SecurityContextHolder` provides access to the current
`SecurityContext`.

It is **not** responsible for loading user details, and it does not
provide `UserDetailsService` to `AuthenticationManager`.

Keep these responsibilities separate:

  -----------------------------------------------------------------------
  Component                           Main responsibility
  ----------------------------------- -----------------------------------
  `UserDetailsService`                Loads user information for
                                      authentication, such as username
                                      and stored password hash.

  `AuthenticationManager`             Coordinates authentication.

  `AuthenticationProvider`            Performs a specific authentication
                                      mechanism.

  `SecurityContext`                   Holds the current authentication
                                      information.

  `SecurityContextHolder`             Provides access to the current
                                      `SecurityContext`.
  -----------------------------------------------------------------------

## 5. JwtAuthenticationFilter

For a JWT-protected request, a component such as
`JwtAuthenticationFilter` processes the incoming token.

At a high level, the filter:

1.  Reads the JWT from the incoming request, commonly from the
    `Authorization: Bearer ...` header.
2.  Asks the JWT service to validate the token and obtain the relevant
    user identity or claims.
3.  Establishes an authenticated `Authentication` object in the
    `SecurityContext`, when the token is valid and the user is not
    already authenticated.
4.  Continues the filter chain so authorization rules and the endpoint
    can be evaluated.

**Remember:** The filter connects the incoming JWT to Spring Security's
authentication context.

## 6. Responsibilities: keep the components distinct

  -----------------------------------------------------------------------
  Component                           Responsibility
  ----------------------------------- -----------------------------------
  `AuthenticationService`             Application-level login logic; in
                                      our implementation, it calls
                                      `AuthenticationManager`.

  `AuthenticationManager`             Coordinates credential
                                      authentication.

  `JwtService`                        Generates, signs, validates, and
                                      reads JWT information.

  `JwtAuthenticationFilter`           Processes JWTs on incoming requests
                                      and establishes authentication in
                                      the security context.

  `SecurityContextHolder`             Provides access to the current
                                      security context.
  -----------------------------------------------------------------------

These are common responsibilities in the design we are learning. A real
project may organize classes differently, but the responsibilities
should remain clear.

## 7. End-to-end JWT flow

### Login request

``` text
Client
  |
  | username + password
  v
AuthController
  |
  v
AuthenticationService
  |
  v
AuthenticationManager
  |
  v
AuthenticationProvider
  |
  | credentials valid
  v
AuthenticationService
  |
  v
JwtService
  |
  | generate and sign JWT
  v
Client receives JWT
```

### Subsequent protected request

``` text
Client
  |
  | Authorization: Bearer <token>
  v
JwtAuthenticationFilter
  |
  v
JwtService validates token
  |
  v
Filter establishes Authentication
in the SecurityContext
  |
  v
Spring Security authorization
  |
  v
Protected endpoint
```

A JWT is not the same thing as a server-side login session. In a typical
stateless JWT setup, the client sends the token with each protected
request, and the server validates it on each request.

## 8. Common misunderstandings corrected today

-   **Incorrect:** `AuthenticationManager` automatically creates and
    sends a JWT.\
    **Correct:** The application must explicitly generate a token,
    commonly through `JwtService`.

-   **Incorrect:** `SecurityContextHolder` loads user details for the
    authentication manager.\
    **Correct:** `UserDetailsService` loads user details.
    `SecurityContextHolder` provides access to the current security
    context.

-   **Incorrect:** `AuthenticationService` is normally the component
    that extracts JWTs from every incoming request.\
    **Correct:** A JWT authentication filter, such as
    `JwtAuthenticationFilter`, normally handles incoming bearer tokens.

-   **Incorrect:** Successful credential verification automatically
    means the user has a persistent login.\
    **Correct:** Authentication, issuing a token, and establishing
    authentication for later requests are distinct steps.

## 9. Quick revision questions

Try answering these without looking at the notes:

1.  Does `AuthenticationManager` automatically generate a JWT after
    successful authentication? Why?
2.  What does `SecurityContextHolder` provide access to?
3.  Which component normally extracts and processes a JWT on an incoming
    protected request?
4.  What is the difference between `UserDetailsService` and
    `SecurityContextHolder`?
5.  What is the role of `JwtService` in the login and request flows?

### Short answers

1.  No. The application must explicitly call a JWT service to generate
    and sign a token.
2.  The current `SecurityContext`, which holds authentication
    information.
3.  A JWT authentication filter, such as `JwtAuthenticationFilter`.
4.  `UserDetailsService` loads user details; `SecurityContextHolder`
    provides access to the current security context.
5.  It handles token operations such as generating, signing, validating,
    and extracting token information.

------------------------------------------------------------------------

## 10. What to study next

Next session, implement the JWT flow in the existing
`spring-security-authentication` module:

1.  Add a `JwtService`.
2.  Generate a signed JWT after successful login.
3.  Return the token from the login endpoint.
4.  Add a `JwtAuthenticationFilter`.
5.  Validate incoming bearer tokens and establish authentication in the
    `SecurityContext`.
6.  Protect an endpoint and test it in Postman.

Do not treat this checklist as completed until each step is implemented
and tested.

------------------------------------------------------------------------

**Session outcome:** Reviewed the separation between credential
authentication, JWT handling, and the Spring Security context. The next
practical goal is to connect these pieces in the existing module.
