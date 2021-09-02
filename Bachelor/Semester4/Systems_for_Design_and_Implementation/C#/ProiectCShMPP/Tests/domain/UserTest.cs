using NUnit.Framework;
using ProiectCShMPP.domain;

namespace Tests.domain
{
    [TestFixture]
    public class UserTest
    {
        [Test]
        public void TestUsername()
        {
            User user = new User(1L)
            {
                Username = "Anne",
                Password = "anneMe"
            };
            Assert.AreEqual("Anne", user.Username);
            user.Username = "Bob";
            Assert.AreEqual("Bob", user.Username);
        }

        [Test]
        public void TestId()
        {
            User user = new User(1L)
            {
                Username = "Anne",
                Password = "anneMe"
            };
            Assert.AreEqual(1L, user.Id);
            user.Id = 3L;
            Assert.AreEqual(3L, user.Id);
        }

        [Test]
        public void TestPassword()
        {
            User user = new User(1)
            {
                Username = "Anne",
                Password = "anneMe"
            };
            Assert.AreEqual("anneMe",user.Password);
            user.Password = "abc";
            Assert.AreEqual("abc", user.Password);
        }
    }
}