package de.hsflensburg.recipe_backend.authentication.entity


import de.hsflensburg.recipe_backend.users.User
import java.time.Instant
import javax.persistence.*


@Entity(name = "refreshtoken")
class RefreshToken(
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User? = null,

    @Column(nullable = false, unique = true)
    val token: String? = null,

    @Column(nullable = false)
    val expiryDate: Instant? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null,
)