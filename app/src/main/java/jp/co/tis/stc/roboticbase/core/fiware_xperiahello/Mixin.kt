package jp.co.tis.stc.roboticbase.core.fiware_xperiahello

interface Mixin {
    val TAG: String
        get() = this.javaClass.name
    val OPERATION_BACK_CODE
        get() = 1001
    val PERMISSION_REQUEST_CODE
        get() = 100
    val OPERATION_RESULT_KEY
        get() = "RESULT_KEY"
    val CERTIFICATE_NAME
        get() = "tis.pem"
}