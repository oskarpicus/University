using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;
using Lab1.MyAdapter;

namespace Lab1.UI
{
    public partial class FormChild : Form
    {
        private readonly int _id;
        private readonly Adapter _adapter;
        private bool _isLoaded;
        private readonly BindingSource _bindingSource = new BindingSource();
        private readonly Dictionary<string, TextBox> _textBoxes = new Dictionary<string, TextBox>();
        
        public FormChild(int id, Adapter adapter)
        {
            InitializeComponent();
            _id = id;
            _adapter = adapter;
            labelChild.Text = Utils.GetResource(Resources.TableChild);
        }

        private void FormChild_Load(object sender, EventArgs e)
        {
            Text = labelChild.Text;
            try
            {
                LoadChildren();
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
            var columns = Utils.GetResource(Resources.ChildColumns).Split(',');
            tableLayoutPanel1.RowCount = columns.Length + 1;
            int rowCount = 0;
            foreach (var column in columns)
            {
                Label label = new Label
                {
                    Text = column + @":",
                    Font = new System.Drawing.Font("Microsoft Sans Serif", 8F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, 0),
                    ForeColor = System.Drawing.Color.FromArgb(249, 247, 247)
                };
                TextBox textBox = new TextBox();
                if (column==Utils.GetChildIdColumn())
                {
                    textBox.ReadOnly = true;
                }
                textBox.DataBindings.Add(new Binding("Text", _bindingSource, column));
                tableLayoutPanel1.Controls.Add(label, 0, rowCount);
                tableLayoutPanel1.Controls.Add(textBox, 1, rowCount);
                _textBoxes.Add(column, textBox);
                rowCount++;
            }
            tableLayoutPanel1.Controls.Add(buttonUpdate, 0, rowCount);
            tableLayoutPanel1.Controls.Add(buttonDelete, 1, rowCount);
        }

        private void dataGridViewBooks_SelectionChanged(object sender, EventArgs e)
        {
            if(_isLoaded && dataGridViewChildren.Rows.Count>0 && dataGridViewChildren.SelectedRows.Count > 0)
                _bindingSource.Position = dataGridViewChildren.SelectedRows[0].Index;
            _isLoaded = true;
        }

        private void buttonUpdate_Click(object sender, EventArgs e)
        {
            try
            {
                IList<string> parameters = new List<string>();
                foreach (var column in Utils.GetResource(Resources.ChildColumns).Split(','))
                {
                    parameters.Add(_textBoxes[column].Text);
                }
                int result = _adapter.UpdateChild(parameters.ToArray());
                if (result == 0)
                    MessageBox.Show(@"Failed to update");
                else
                {
                    MessageBox.Show(@"Updated successfully");
                }
                LoadChildren();
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
                int id = (int) dataGridViewChildren.SelectedRows[0].Cells[Utils.GetChildIdColumn()].Value;
                if (_adapter.DeleteChild(id) == 0)
                {
                    MessageBox.Show(@"Failed to delete");
                }
                else
                {
                    MessageBox.Show(@"Deleted successfully");
                    LoadChildren();
                }
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }

        private void LoadChildren()
        {
            dataGridViewChildren.DataSource = _adapter.GetChildrenById(_id);
            _bindingSource.DataSource = dataGridViewChildren.DataSource;
        }
    }
}