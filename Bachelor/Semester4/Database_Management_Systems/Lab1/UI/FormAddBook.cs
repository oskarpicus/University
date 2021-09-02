using System;
using System.Windows.Forms;
using Lab1.MyAdapter;

namespace Lab1.UI
{
    public partial class FormAddBook : Form
    {
        private readonly Adapter _adapter = new Adapter();
        
        public FormAddBook(int eid)
        {
            InitializeComponent();
            textBoxEid.Text = eid.ToString();
        }

        private void buttonSave_Click(object sender, EventArgs e)
        {
            try
            {
                if (_adapter.AddBook(textBoxTitle.Text, Int32.Parse(textBoxYear.Text), Int32.Parse(textBoxEid.Text)) !=
                    0)
                {
                    MessageBox.Show("Successfully added book");
                }
                else
                {
                    MessageBox.Show("Failed to add book");
                }
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }
    }
}