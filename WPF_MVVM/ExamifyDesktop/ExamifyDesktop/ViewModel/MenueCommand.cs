using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Input;

namespace ExamifyDesktop.ViewModel
{
    public class MenueCommand : ICommand
    {
        public event EventHandler CanExecuteChanged;

        public bool CanExecute(object parameter)
        {
            return true;
        }

        public void Execute(object parameter)
        {                                //  Application ist ein Singleton
            MainWindow mw = (MainWindow)Application.Current.MainWindow;

            if (parameter?.ToString() != "Windowabout")
                mw.contentctl.Children.Clear();

            switch (parameter.ToString())
            {
                case "Examify":
                    mw.contentctl.Children.Add(new Examify());
                    break;
                case "Bar":
                    mw.contentctl.Children.Add(new Bar());
                    break;
                case "Baz":
                    mw.contentctl.Children.Add(new Baz());
                    break;
                default:
                    MessageBox.Show(parameter.ToString() + " ist kein sinnvoller Menüeintrag!!");
                    break;
            }



        }
    }
}
