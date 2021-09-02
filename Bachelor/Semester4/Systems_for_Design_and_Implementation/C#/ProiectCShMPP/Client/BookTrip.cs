using System;
using System.Windows.Forms;
using ProiectCShMPP.domain;
using Services;

namespace GUI
{
    public partial class BookTrip : Form
    {
        private readonly IService _service;
        private readonly Trip _trip;
        private readonly User _user;
        
        public BookTrip(IService service, Trip trip, User user)
        {
            _service = service;
            _trip = trip;
            _user = user;
            InitializeComponent();
        }

        private void buttonMakeReservation_Click(object sender, EventArgs e)
        {
            string clientName = textBoxClientName.Text;
            string phoneNumber = textBoxPhoneNumber.Text;
            int tickets = (int) numericUpDownTickets.Value;

            if (clientName=="" || phoneNumber=="")
            {
                MessageBox.Show(@"You haven't filled all forms !");
                return;
            }

            try
            {
                var result = _service.BookTrip(clientName, phoneNumber, tickets, _trip, _user);
                MessageBox.Show(result == null ? @"Reservation successfully saved" : @"Failed to make reservation");
                Close();
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }
    }
}