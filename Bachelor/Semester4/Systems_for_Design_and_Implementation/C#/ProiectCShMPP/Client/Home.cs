using System;
using System.Drawing;
using System.Windows.Forms;
using ProiectCShMPP.domain;
using Services;

namespace GUI
{
    public partial class Home : Form, IObserver
    {
        private readonly IService _service;
        public User LoggedUser { get; set; }
        
        public Home(IService service)
        {
            _service = service;
            InitializeComponent();
        }

        private void buttonLogOut_Click(object sender, EventArgs e)
        {
            _service.LogOut(LoggedUser, this);
            Close();
        }

        private void Home_Load(object sender, EventArgs e)
        {
            labelGreeting.Text = @"Hello " + LoggedUser.Username + @", here are all the trips";
            dataGridViewTrips.DataSource = _service.GetAllTrips();
            ColorRows();
        }

        private void buttonSearch_Click(object sender, EventArgs e)
        {
            try
            {
                String destination = textBoxDestination.Text;
                DateTime from = dateTimePickerFrom.Value;
                DateTime to = dateTimePickerTo.Value;

                if (destination == "")
                {
                    MessageBox.Show(@"You need to complete all fields");
                    return;
                }

                SearchTrip searchTrip = new SearchTrip(_service, destination, from, to);
                searchTrip.Show();
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception);
            }
        }

        private void buttonBookTrip_Click(object sender, EventArgs e)
        {
            try
            {
                if (dataGridViewTrips.SelectedRows.Count == 0)
                {
                    MessageBox.Show(@"You have not selected any trips !");
                    return;
                }

                DataGridViewRow row = dataGridViewTrips.SelectedRows[0];
                Trip trip = new Trip((long) row.Cells["Id"].Value)
                {
                    Destination = (string) row.Cells["Destination"].Value,
                    DepartureTime = (DateTime) row.Cells["DepartureTime"].Value,
                    Price = (double) row.Cells["Price"].Value,
                    Seats = (int) row.Cells["Seats"].Value,
                    TransportFirm = (string) row.Cells["TransportFirm"].Value
                };
                BookTrip bookTrip = new BookTrip(_service, trip, LoggedUser);
                bookTrip.Show();
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception);
            }
        }

        public void TripChanged(Trip trip)
        {
            foreach (DataGridViewRow row in dataGridViewTrips.Rows)
            {
                if ((long)row.Cells["Id"].Value == trip.Id)
                {
                    row.Cells["Seats"].Value = trip.Seats;
                    break;
                }
            }
            if(trip.Seats==0)
                ColorRows();
        }

        private void ColorRows()
        {
            foreach (DataGridViewRow row in dataGridViewTrips.Rows)
            {
                if (Convert.ToInt32(row.Cells["seats"].Value) <= 0)
                {
                    row.DefaultCellStyle.BackColor = Color.Red;
                }
            }
        }
    }
}