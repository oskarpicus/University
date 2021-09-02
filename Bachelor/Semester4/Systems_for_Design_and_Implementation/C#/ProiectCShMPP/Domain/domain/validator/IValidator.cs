namespace ProiectCShMPP.domain.validator
{
    public interface IValidator<TId, E> where E : Entity<TId>
    {
        /// <summary>
        /// Validates a given entity
        /// </summary>
        /// <param name="entity"> entity to be validated </param>
        /// <exception cref="ValidationException"> entity is not valid </exception>
        void Validate(E entity);
    }
}