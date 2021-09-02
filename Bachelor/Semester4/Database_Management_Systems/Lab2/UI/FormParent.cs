using System;
using System.Windows.Forms;
using Lab1.MyAdapter;

namespace Lab1.UI
{
    public partial class FormParent : Form
    {
        private Adapter _adapter;
        public FormParent()
        {
            InitializeComponent();
        }

        private void FormParent_Load(object sender, EventArgs e)
        {
            labelParent.Text = Utils.GetResource(Resources.TableParent);
            Text = Utils.GetResource(Resources.TableParent);
            buttonAddChild.Text = @"Add "+Utils.GetResource(Resources.TableChild);
            try
            {
                _adapter = new Adapter();
                dataGridViewParents.DataSource = _adapter.GetAllParents();
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }

        private void FormParent_DoubleClick(object sender, EventArgs e)
        {
            try
            {
                var columnId = Utils.GetParentIdColumn();
                int id = (int) dataGridViewParents.SelectedRows[0].Cells[columnId].Value;
                FormChild formChild = new FormChild(id, _adapter);
                formChild.Show();
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
                var columnId = Utils.GetParentIdColumn();
                int id = (int) dataGridViewParents.SelectedRows[0].Cells[columnId].Value;
                FormAddChild formAddChild = new FormAddChild(id, _adapter);
                formAddChild.Show();
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }
    }
}