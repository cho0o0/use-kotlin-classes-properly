package com.hitoda.sample

import com.google.gson.Gson
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class SampleTestCases {
    @Test
    fun `only correct phone contact information can be created`() {
        assertDoesNotThrow { PhoneContactInformation(phoneNumber = PhoneNumber("080-1234-5678")) }
        assertThrows<IllegalArgumentException> { PhoneContactInformation(phoneNumber = PhoneNumber("123-567-789")) }
    }

    @Test
    fun `only correct email contact information can be created`() {
        assertDoesNotThrow { EmailContactInformation(emailAddress = EmailAddress("test@example.org")) }
        assertThrows<IllegalArgumentException> { EmailContactInformation(emailAddress = EmailAddress("wrong#email")) }
    }

    @Test
    fun `contact information can be created`() {
        val contact: ContactInformation = PhoneContactInformation(phoneNumber = PhoneNumber("080-1234-5678"))

        when(contact) {
            is PhoneContactInformation -> println("a")
            is EmailContactInformation -> println("b")
        }

        assertEquals(PhoneContactInformation::class.simpleName, contact.type)
        assertEquals(VerifyStatus.NOT_VERIFIED, contact.verifyStatus)

        contact.verifyInformation()

        assertEquals(VerifyStatus.VERIFIED, contact.verifyStatus)

    }

    data class Person(val name: String, val contact: ContactInformation)

    @Test
    fun `contact information can be serialized`() {
        val person = Person(name = "Jack", contact = PhoneContactInformation(phoneNumber = PhoneNumber("080-1234-5678")))
        println(Gson().toJson(person))
    }
}