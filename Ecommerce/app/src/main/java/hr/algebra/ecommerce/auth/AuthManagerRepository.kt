package hr.algebra.ecommerce.auth

enum class AuthManagerRepository {
    INSTANCE;

    private val authManager: AuthManager = FirebaseAuthManager()
    fun getAuthManager() : AuthManager = authManager
}