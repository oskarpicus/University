using System;

namespace ProiectCShMPP.domain
{
    [Serializable]
    public class User : Entity<long>
    {
        public User(long id) : base(id)
        {
        }

        public string Username
        {
            get;
            set;
        }

        public string Password
        {
            get;
            set;
        }

        public override string ToString()
        {
            return base.ToString()+" "+Username+" "+Password;
        }

        public override bool Equals(object obj)
        {
            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }

            User other = (User) obj;
            return Id == other.Id && Username == other.Username && Password == other.Password;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }
}