using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using log4net;
using log4net.Config;
using ProiectCShMPP.domain;
using ProiectCShMPP.domain.validator;
using ProiectCShMPP.utils;

namespace ProiectCShMPP.repository
{
    public abstract class AbstractDBRepository<ID,E> : IRepository<ID,E> where E : Entity<ID>
    {
        protected static readonly ILog _logger = LogManager.GetLogger(typeof(AbstractDBRepository<ID,E>));
        private readonly IValidator<ID, E> _validator;
        
        public AbstractDBRepository(IValidator<ID,E> validator)
        {
            _validator = validator;
        }

        static AbstractDBRepository()
        {
            XmlConfigurator.Configure(new FileInfo("log4net.xml"));
        }
        
        public E Save(E entity)
        {
            _logger.InfoFormat("Entering Save {0}", entity);
            if (entity == null)
            {
                _logger.Error("Saving null");
                throw new ArgumentException();
            }
            _validator.Validate(entity); 
            int result = 0;
            using (IDbConnection connection = DbUtils.GetConnection())
            {
                using (IDbCommand command = GetSaveCommand(connection, entity))
                {
                    result = command.ExecuteNonQuery();
                    _logger.InfoFormat("{0} rows affected", result);
                }
            }
            _logger.InfoFormat("Exiting Save {0}", entity);
            return result == 0 ? entity : null;
        }

        public E Delete(ID id)
        {
            _logger.InfoFormat("Entering Delete {0}", id);
            if (id == null)
            {
                _logger.Error("Deleting null");
                throw new ArgumentException();
            }
            E entity = Find(id);
            if (entity != null)
            {
                using (IDbConnection connection = DbUtils.GetConnection())
                {
                    using (IDbCommand command = GetDeleteCommand(connection, id))
                    {
                        int result = command.ExecuteNonQuery();
                        _logger.InfoFormat("{0} rows affected",result);
                        entity = result == 0 ? null : entity;
                    }
                }
            }
            _logger.InfoFormat("Exiting Delete {0} with result {1}", id, entity);
            return entity;
        }

        public E Update(E entity)
        {
            _logger.InfoFormat("Entering Update {0}", entity);
            if (entity == null)
            {
                _logger.Error("Updating null");
                throw new ArgumentException();
            }
            _validator.Validate(entity);
            int result;
            using (IDbConnection connection = DbUtils.GetConnection())
            {
                using (IDbCommand command = GetUpdateCommand(connection, entity))
                {
                    result = command.ExecuteNonQuery();
                    _logger.InfoFormat("{0} rows affected", result);
                }
            }
            _logger.InfoFormat("Exiting Update {0}", entity);
            return result == 0 ? entity : null;
        }

        public E Find(ID id)
        {
            _logger.InfoFormat("Entering Find {0}", id);
            if (id == null)
            {
                _logger.Error("Finding null");
                throw new ArgumentException();
            }
            E result = null;
            using (IDbConnection connection = DbUtils.GetConnection())
            {
                using (IDbCommand command = GetFindCommand(connection, id))
                {
                    using (IDataReader dataReader = command.ExecuteReader())
                    {
                        if (dataReader.Read())
                        {
                            result = ExtractEntity(dataReader);
                        }
                    }
                }                
            }
            _logger.InfoFormat("Exiting Find {0} with result {1}", id, result);
            return result;
        }

        public IEnumerable<E> FindAll()
        {
            _logger.InfoFormat("Entering FindAll");
            IList<E> list = new List<E>();
            using (IDbConnection connection = DbUtils.GetConnection())
            {
                using (IDbCommand command = GetFindAllCommand(connection))
                {
                    using (IDataReader dataReader = command.ExecuteReader())
                    {
                        while (dataReader.Read())
                        {
                            list.Add(ExtractEntity(dataReader));
                        }
                    }
                }
            }
            _logger.InfoFormat("Exiting FindAll with {0}", list);
            return list;
        }

        protected abstract IDbCommand GetSaveCommand(IDbConnection connection, E entity);
        
        protected abstract IDbCommand GetDeleteCommand(IDbConnection connection, ID id);
        
        protected abstract IDbCommand GetUpdateCommand(IDbConnection connection, E entity);
        
        protected abstract IDbCommand GetFindCommand(IDbConnection connection, ID id);
        
        protected abstract IDbCommand GetFindAllCommand(IDbConnection connection);
        
        /// <summary>
        /// Method for obtaining an entity based on Data Reader
        /// </summary>
        /// <param name="reader">the Data Reader, must point to a valid row</param>
        /// <returns>the corresponding entry from reader's current row</returns>
        protected abstract E ExtractEntity(IDataReader reader);
    }
}