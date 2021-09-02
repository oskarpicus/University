using System;
using System.Drawing;
using System.Windows.Forms;
using Services;

namespace GUI
{
    public partial class SearchTrip : Form
    {
        private readonly IService _service;
        private readonly string _destination;
        private readonly DateTime _from;
        private readonly DateTime _to;
        
        public SearchTrip(IService service, string destination, DateTime from, DateTime to)
        {
            _service = service;
            _destination = destination;
            _from = from;
            _to = to;
            InitializeComponent();
        }

        private void SearchTrip_Load(object sender, EventArgs e)
        {
            labelGreeting.Text = @"Trips in " + _destination + @" between " + _from + @" and " + _to;
            var trips = _service.FindTrips(_destination, _from, _to);
            dataGridViewTrips.DataSource = trips;
            if (trips.Count == 0)
            {
                MessageBox.Show(@"There are no trips to show !");
                Close();
                return;
            }
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