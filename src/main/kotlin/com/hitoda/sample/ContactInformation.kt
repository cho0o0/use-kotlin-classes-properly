package com.hitoda.sample

sealed class ContactInformation {
    var verifyStatus: VerifyStatus = VerifyStatus.NOT_VERIFIED
        private set
    abstract val type: String

    fun verifyInformation() {
        require(verifyStatus != VerifyStatus.VERIFYING)
        verifyStatus = VerifyStatus.VERIFYING

        verifyStatus = if(this.verify()) VerifyStatus.VERIFIED else VerifyStatus.NOT_VERIFIED
    }

    abstract fun verify() : Boolean
}

data class PhoneContactInformation(
    override val type: String = PhoneContactInformation::class.simpleName!!,
    val phoneNumber: PhoneNumber,
    ) : ContactInformation() {

    fun dial() {
        println("dial to $phoneNumber")
    }

    override fun verify(): Boolean {
        println("verify via sms")
        return true
    }
}

data class EmailContactInformation(
    override val type: String = EmailContactInformation::class.simpleName!!,
    val emailAddress: EmailAddress,
): ContactInformation() {

    fun mail() {
        println("mail to $emailAddress")
    }

    override fun verify(): Boolean {
        println("verify via email")
        return true
    }
}

inline class PhoneNumber(val value: String) {
    init {
        require(value.matches(Regex("\\A(((0(\\d{1}[-(]?\\d{4}|\\d{2}[-(]?\\d{3}|\\d{3}[-(]?\\d{2}|\\d{4}[-(]?\\d{1}|[5789]0[-(]?\\d{4})[-)]?)|\\d{1,4}\\-?)\\d{4}|0120[-(]?\\d{3}[-)]?\\d{3})\\z")))
    }
}

inline class EmailAddress(val value: String) {
    init {
        require(value.matches(Regex("\\A[a-zA-Z0-9.!\\#\$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\\z")))
    }
}

enum class VerifyStatus {
    NOT_VERIFIED,
    VERIFYING,
    VERIFIED
}
