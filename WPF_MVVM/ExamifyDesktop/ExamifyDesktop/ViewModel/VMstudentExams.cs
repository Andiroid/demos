using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;


namespace ExamifyDesktop.ViewModel
{
    /// ExamifyDesktopDBEntities
    class VMstudentExams : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;

        public IEnumerable<s_student> AllStudents
        {
            get
            {
                using (ExamifyDesktopDBEntities db = new ExamifyDesktopDBEntities())
                { 
                    var erg = (from s in db.s_student
                               orderby s.s_last_name
                               select s).ToList();
                    return erg;
                }
            }
        }



        private int selectedStudent;

        public int SelectedStudent
        {
            get { return selectedStudent; }
            set
            {
                selectedStudent = value;
                PropertyChanged(this, new PropertyChangedEventArgs("SelectedStudent"));
                PropertyChanged(this, new PropertyChangedEventArgs("StudentExams"));

            }
        }

        public IEnumerable<e_exam> StudentExams
        {
            get
            {
                using (ExamifyDesktopDBEntities db = new ExamifyDesktopDBEntities())
                {
                    var erg = from ex in db.e_exam //.Include("fk from other table")
                              where ex.s_student.s_id == SelectedStudent
                              select ex;
                    return erg.ToList();
                }

            }
        }

        private e_exam selectedExam;

        public e_exam SelectedExam
        {
            get { return selectedExam; }
            set
            {
                selectedExam = value;
                PropertyChanged(this, new PropertyChangedEventArgs("SelectedExam"));
            }
        }


        public ICommand ChangeCommand
        { get { return new DelegateCommand(ChangeExecute, CanExecute); } }

        public ICommand DeleteCommand
        { get { return new DelegateCommand(DeleteExecute, CanExecute); } }
        public ICommand NewCommand
        { get { return new DelegateCommand(NewExecute, CanNewExecute); } }


        public bool CanExecute(object param)
        {
            if (SelectedExam != null) return true;
            return false;
        }
        public bool CanNewExecute(object param)
        {
            if (SelectedStudent != 0) return true;
            return false;
        }

        public void ChangeExecute(object param)
        {
            if (SelectedExam != null)
            {
                e_exam vm = SelectedExam;
                ViewExam v = new ViewExam();
                v.DataContext = vm;
                v.ShowDialog();
                if (v.DialogResult.HasValue && v.DialogResult.Value)
                {
                    using (ExamifyDesktopDBEntities db = new ExamifyDesktopDBEntities())
                    {
                        db.Entry(vm).State = EntityState.Modified;
                        db.SaveChanges();
                    }
                }
            }
        }

        public void NewExecute(object param)
        {
            if (SelectedStudent != 0)
            {

                e_exam vm = new e_exam { e_s_id = SelectedStudent };

                if (vm.e_title == "") {
                    PropertyChanged("error", new PropertyChangedEventArgs("errorText"));
                }

                ViewExam v = new ViewExam();
                
                v.DataContext = vm;
                v.ShowDialog();
                if (v.DialogResult.HasValue && v.DialogResult.Value)
                {
                    using (ExamifyDesktopDBEntities db = new ExamifyDesktopDBEntities())
                    {
                        //db.Entry(vm).State = EntityState.Added;
                        db.e_exam.Add(vm);
                        db.SaveChanges();
                        PropertyChanged(this, new PropertyChangedEventArgs("StudentExams"));
                    }
                }
            }
        }

        public void DeleteExecute(object param)
        {
            if (SelectedExam != null)
            {
                using (ExamifyDesktopDBEntities db = new ExamifyDesktopDBEntities())
                {
                    db.Entry(SelectedExam).State = EntityState.Deleted;
                    db.SaveChanges();
                    PropertyChanged(this, new PropertyChangedEventArgs("StudentExams"));
                }
            }
        }






    }
}
