using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Data;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.Data.SqlClient;

namespace Examify.Models
{
    public class ExamModel
    {
        public static string needle { get; set; }
        public int id { get; set; }

        [Display(Name = "Title")]
        [Required(ErrorMessage = "Title please")]
        [StringLength(50, MinimumLength = 1, ErrorMessage = "Invalid title length")]
        public string title { get; set; }
        
        [Display(Name = "Type")]
        [Required(ErrorMessage = "Type please")]
        [StringLength(50, MinimumLength = 1, ErrorMessage = "Invalid type length")]
        public string type { get; set; }

        [Display(Name = "Subject")]
        [Required(ErrorMessage = "Subject please")]
        [StringLength(50, MinimumLength = 1, ErrorMessage = "Invalid subject length")]
        public string subject { get; set; }

        [Display(Name = "Date")]
        [Required(ErrorMessage = "Date please")]
        [DataType(DataType.Date, ErrorMessage = "Please valid date")]
        // [Range(typeof(DateTime), "01/01/1900", "01/01/2100", ErrorMessage = "Date is out of Range")]
        public DateTime date { get; set; }

        private static readonly string SqlConnectionString = Environment.GetEnvironmentVariable("SQL_CONNECTION_STRING");

        public static bool insertNewExam(ExamModel model)
        {
            using (SqlConnection connection = new SqlConnection(SqlConnectionString))
            {
                connection.Open();
                SqlCommand command = new SqlCommand(null, connection);

                if (model.id == 0) {
                    command.CommandText =
                        "INSERT INTO exam (e_title, e_type, e_subject, e_date, e_complete) VALUES (@e_title, @e_type, @e_subject, @e_date, @e_complete)";
                    SqlParameter e_title = new SqlParameter("@e_title", SqlDbType.Text, 100);
                    SqlParameter e_type = new SqlParameter("@e_type", SqlDbType.Text, 100);
                    SqlParameter e_subject = new SqlParameter("@e_subject", SqlDbType.Text, 100);
                    SqlParameter e_date = new SqlParameter("@e_date", SqlDbType.DateTime, 100);
                    SqlParameter e_complete = new SqlParameter("@e_complete", SqlDbType.TinyInt, 100);

                    e_title.Value = model.title;
                    e_type.Value = model.type;
                    e_subject.Value = model.subject;
                    e_date.Value = model.date;
                    e_complete.Value = 0;

                    command.Parameters.Add(e_title);
                    command.Parameters.Add(e_type);
                    command.Parameters.Add(e_subject);
                    command.Parameters.Add(e_date);
                    command.Parameters.Add(e_complete);
                } else {
                    command.CommandText =
                        "UPDATE exam SET e_title = @e_title, e_type = @e_type, e_subject = @e_subject, e_date = @e_date WHERE e_id = @e_id";
                    SqlParameter e_id = new SqlParameter("@e_id", SqlDbType.Int, 100);
                    SqlParameter e_title = new SqlParameter("@e_title", SqlDbType.Text, 100);
                    SqlParameter e_type = new SqlParameter("@e_type", SqlDbType.Text, 100);
                    SqlParameter e_subject = new SqlParameter("@e_subject", SqlDbType.Text, 100);
                    SqlParameter e_date = new SqlParameter("@e_date", SqlDbType.DateTime, 100);

                    e_id.Value = model.id;
                    e_title.Value = model.title;
                    e_type.Value = model.type;
                    e_subject.Value = model.subject;
                    e_date.Value = model.date;

                    command.Parameters.Add(e_id);
                    command.Parameters.Add(e_title);
                    command.Parameters.Add(e_type);
                    command.Parameters.Add(e_subject);
                    command.Parameters.Add(e_date);
                }

                command.Prepare();

                if (command.ExecuteNonQuery() != 0) {
                    return true;
                }
            }
            return false;
        }

        public static bool completeExam(int examId, int status)
        {
            using (SqlConnection connection = new SqlConnection(SqlConnectionString))
            {
                connection.Open();
                SqlCommand command = new SqlCommand(null, connection);

                command.CommandText = "UPDATE exam SET e_complete = @status  WHERE e_id = @id";

                SqlParameter idParam = new SqlParameter("@id", SqlDbType.Int, 0);
                SqlParameter statusParam = new SqlParameter("@status", SqlDbType.TinyInt, 0);
                idParam.Value = examId;
                statusParam.Value = status;
                command.Parameters.Add(idParam);
                command.Parameters.Add(statusParam);
                command.Prepare();

                if (command.ExecuteNonQuery() != 0) {
                    return true;
                }
            }

            return false;
        }

        public static DataTable fetchAllExams()
        {
            using (SqlConnection connection = new SqlConnection(SqlConnectionString))
            {
                connection.Open();
                SqlCommand command = new SqlCommand(null, connection);

                if (needle != null) {
                    command.CommandText = "SELECT * FROM exam WHERE (e_title LIKE '%' + @needle + '%') ORDER BY e_date ASC";
                    SqlParameter idParam = new SqlParameter("@needle", SqlDbType.VarChar, 200);
                    idParam.Value = needle;
                    command.Parameters.Add(idParam);
                } else {
                    command.CommandText = "SELECT * FROM exam ORDER BY e_date ASC";
                }

                command.Prepare();

                SqlDataAdapter dataAdapter = new SqlDataAdapter(command);
                DataTable dataTable = new DataTable();
                dataAdapter.Fill(dataTable);

                return dataTable;
            }
        }

        public static bool deleteExam(int examId) {

            using (SqlConnection connection = new SqlConnection(SqlConnectionString))
            {
                connection.Open();
                SqlCommand command = new SqlCommand(null, connection);

                command.CommandText = "DELETE FROM exam WHERE e_id = @id";

                SqlParameter idParam = new SqlParameter("@id", SqlDbType.Int, 0);
                idParam.Value = examId;
                command.Parameters.Add(idParam);

                command.Prepare();

                if (command.ExecuteNonQuery() != 0) {
                    return true;
                }
            }
            return false;
        }
    }
}
