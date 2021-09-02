using System;
using System.Windows.Forms;
using Lab1.MyAdapter;

namespace Lab1.UI
{
    public partial class FormPublishingHouses : Form
    {
        private Adapter _adapter;
        public FormPublishingHouses()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            try
            {
                _adapter = new Adapter();
                dataGridViewPublishingHouses.DataSource = _adapter.GetAllPublishingHouses();
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
            
        }

        private void FormPublishingHouses_DoubleClick(object sender, EventArgs e)
        {
            try
            {
                int eid = (int) dataGridViewPublishingHouses.SelectedRows[0].Cells["eid"].Value;
                string name = (string) dataGridViewPublishingHouses.SelectedRows[0].Cells["nume"].Value;
                FormBooks formBooks = new FormBooks(eid, name);
                formBooks.Show();
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }

        private void buttonAddBook_Click(object sender, EventArgs e)
        {
            try
            {
                int eid = (int) dataGridViewPublishingHouses.SelectedRows[0].Cells["eid"].Value;
                FormAddBook formAddBook = new FormAddBook(eid);
                formAddBook.Show();
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }
    }
}