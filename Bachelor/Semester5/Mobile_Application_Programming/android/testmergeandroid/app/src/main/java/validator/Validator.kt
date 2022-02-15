package validator

interface Validator<E> {
    fun validate(e: E)
}