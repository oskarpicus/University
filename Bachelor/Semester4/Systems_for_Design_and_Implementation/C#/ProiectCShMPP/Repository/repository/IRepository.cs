using System;
using System.Collections.Generic;
using ProiectCShMPP.domain;
using ProiectCShMPP.domain.validator;

namespace ProiectCShMPP.repository
{
    public interface IRepository<TId,E> where E : Entity<TId>
    {
        /// <summary>
        /// Method for saving an entity
        /// </summary>
        /// <param name="entity">the entity to be saved</param>
        /// <returns>
        /// - null, if it was successfully saved
        /// - the entity, otherwise (e.g. id already exists)
        /// </returns>
        /// <exception cref="ArgumentException">if entity is null</exception>
        /// <exception cref="ValidationException">if entity is not valid</exception>
        E Save(E entity);

        /// <summary>
        /// Method for deleting an entity by id
        /// </summary>
        /// <param name="id">the id of the entity to be deleted</param>
        /// <returns>
        /// - null, if there is no entity with that id
        /// - the removed entity, otherwise
        /// </returns>
        /// <exception cref="ArgumentException">if id is null</exception>
        E Delete(TId id);

        /// <summary>
        /// Method for updating an entity
        /// </summary>
        /// <param name="entity">the entity to be updated</param>
        /// <returns>
        /// - null, if the entity was successfully updated
        /// - the entity, otherwise
        /// </returns>
        /// <exception cref="ArgumentException">if entity is null</exception>
        /// <exception cref="ValidationException">if entity is not valid</exception>
        E Update(E entity);

        /// <summary>
        /// Method for finding an entity
        /// </summary>
        /// <param name="id">id of the desired entity</param>
        /// <returns>
        /// - null, if there is no entity with that id
        /// - the entity, otherwise
        /// </returns>
        /// <exception cref="ArgumentException">if id is null</exception>
        E Find(TId id);

        /// <summary>
        /// Method for retrieving all data
        /// </summary>
        /// <returns>an enumeration of all data</returns>
        IEnumerable<E> FindAll();
    }
}