using System;
using System.Windows.Forms;
using Lab1.MyAdapter;

namespace Lab1.UI
{
    public partial class FormBooks : Form
    {
        private readonly int _eid;
        private Adapter _adapter;
        private bool _isLoaded;
        public FormBooks(int eid, string name)
        {
            InitializeComponent();
            _eid = eid;
            labelCarti.Text += " de la " + name;
        }

        private void FormBooks_Load(object sender, EventArgs e)
        {
            try
            {
                _adapter = new Adapter();
                LoadBooks();
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }

        private void dataGridViewBooks_SelectionChanged(object sender, EventArgs e)
        {
            if (dataGridViewBooks.Rows.Count>0 && dataGridViewBooks.SelectedRows.Count > 0 && _isLoaded)
            {
                try
                {
                    textBoxTitlu.Text = (string) dataGridViewBooks.SelectedRows[0].Cells["titlu"].Value;
                    textBoxAn.Text = dataGridViewBooks.SelectedRows[0].Cells["an"].Value.ToString();
                    textBoxEid.Text = dataGridViewBooks.SelectedRows[0].Cells["eid"].Value.ToString();
                }
                catch (Exception exception)
                {
                    MessageBox.Show(exception.Message);
                }
            }
            _isLoaded = true;
        }

        private void buttonUpdate_Click(object sender, EventArgs e)
        {
            try
            {
                string title = textBoxTitlu.Text;
                int year = Int32.Parse(textBoxAn.Text);
                int eid = Int32.Parse(textBoxEid.Text);
                int cid = (int) dataGridViewBooks.SelectedRows[0].Cells["cid"].Value;
                int result = _adapter.UpdateBook(cid, title, year, eid);
                if (result == 0)
                    MessageBox.Show("Failed to update");
                else
                {
                    MessageBox.Show("Updated successfully");
                }
                LoadBooks();
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }

        private void buttonDelete_Click(object sender, EventArgs e)
        {
            try
            {
                int cid = (int) dataGridViewBooks.SelectedRows[0].Cells["cid"].Value;
                if (_adapter.DeleteBook(cid) == 0)
                {
                    MessageBox.Show("Failed to delete");
                }
                else
                {
                    MessageBox.Show("Deleted successfully");
                    LoadBooks();
                }
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }

        private void LoadBooks()
        {
            dataGridViewBooks.DataSource = _adapter.GetBooksByEid(_eid);
        }
    }
}