using System;
using System.Data;
using Npgsql;
using NUnit.Framework;
using ProiectCShMPP.domain;
using ProiectCShMPP.domain.validator;
using ProiectCShMPP.repository;

namespace Tests.repository
{
    [TestFixture]
    public class UserDbRepositoryTest
    {
        private readonly UserDbRepository _repository = new UserDbRepository(new UserValidator());
        
        [Test]
        public void TestFind()
        {
            Assert.AreEqual(null, _repository.Find(1902442L));
            User result = _repository.Find(2L);
            Assert.AreEqual(2, result.Id);
            Assert.AreEqual("Andrew", result.Username);
            Assert.AreEqual("Right", result.Password);
        }
    }
}