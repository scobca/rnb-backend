package org.itmo.rnbbackend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var username: String = ""

    @Column(nullable = false)
    var email: String = ""

    @Column(nullable = false)
    var password: String = ""

    override fun toString(): String {
        return "UserEntity(id=$id, username='$username', email='$email', password='$password')"
    }
}