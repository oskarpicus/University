using NUnit.Framework;
using ProiectCShMPP.domain;
using ProiectCShMPP.domain.validator;

namespace Tests.domain.validator
{
    [TestFixture]
    public class UserValidatorTest
    {
        private readonly IValidator<long, User> _validator = new UserValidator();
        
        [Test]
        public void TestValidate()
        {
            User user = new User(1L)
            {
                Password = "",
                Username = ""
            };
            try
            {
                _validator.Validate(user);
                Assert.Fail();
            }
            catch (ValidationException e)
            {
                Assert.AreEqual("Invalid username\nInvalid password\n",e.Message);
            }
            _validator.Validate(new User(2L)
            {
                Password = "notAVeryLongPassword",
                Username = "Username"
            });
        }
    }
}