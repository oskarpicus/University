using System;
using System.Windows.Forms;
using ProiectCShMPP.domain;
using Services;

namespace GUI
{
    public partial class LogIn : Form
    {
        private readonly IService _service;
        public LogIn(IService service)
        {
            _service = service;
            InitializeComponent();
        }

        private void buttonLogIn_Click(object sender, EventArgs e)
        {
            String username = textBoxUsername.Text;
            String password = textBoxPassword.Text;
            if (username == "" || password == "")
            {
                MessageBox.Show(@"You have forms unfilled !");
                return;
            }

            Home home = new Home(_service);
            try
            {
                User user = _service.FindUser(username, password, home); 
                if (user == null)
                {
                    MessageBox.Show(@"Wrong credentials !");
                }
                else
                {
                    home.LoggedUser = user;
                    Hide();
                    home.Closed += (s, args) => Show();
                    home.Show();
                }
            }
            catch (TourismAgencyException exception)
            {
                MessageBox.Show(exception.Message);
            }
        }
    }
}