   M S B A M L   `   `   `  ����I  EExamifyDesktop, Version=1.0.0.0, Culture=neutral, PublicKeyToken=nullC&clr-namespace:ExamifyDesktop.ViewModelExamifyDesktop.ViewModel  5          ExamifyDesktop.Examify   5      R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5      R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5      Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5      @d2http://schemas.microsoft.com/expression/blend/2008     5      :localvm&clr-namespace:ExamifyDesktop.ViewModel     5        � 	Resources  5	         vm S� 
ObjectType-  'ExamifyDesktop.ViewModel.VMstudentExams��%"   &	        S� 5
   
   "  6)   5      .��5      �  � DataContext 6   ��  �� Source# [   � ColumnDefinitions 5   
   �� 5      $��1* �6   �� 5      $��1* �6   �� 5      $��1* �6   .+�5   
   �� -   5        $��0��5       �� SelectedIndex$ 0��5       �� DisplayMemberPath$ s_last_name��5       �� SelectedValuePath$
 s_id��5      ��5      ��  �� Path$ AllStudents�	 �� SelectedValue	 5      �� $ SelectedStudent�
 �� Mode$
 OneWayToSource=� �� BindsDirectlyToSource . 6r   �� 5   
   $��1��6   ��5      �� $ StudentExams� �� SelectedItem 5      �� $ SelectedExam�$
 OneWayToSource=� . ��5      �� 5      .R�5      �� �� 5      �6   �� *	e_title+5      5       5"      �� 5#   
   $��2��6   $��5q�6%   .+�5$      ��  6   �� *SelectedExam+.+�5%      �� $��12(�6   $��Titel��6+   �� 5&      $
��Bold"�6   $��14(�6/   ��6=   �� *	e_title+�� 5'      $��12(�6   $
��Fach��6*   �� 5(      $
��Bold"�6   $��14(�6/   ��6=   �� *	e_subject+�� 5)      $��12(�6   $	��Typ��6*   �� 5*      $
��Bold"�6   $��14(�6/   ��6=   �� *e_type+�� 5+      $��12(�6   $��Datum��6*   �� 5,      $
��Bold"�6   $��14(�6/   ��6=   �� *e_date+ �� StringFormat$ d�� �� ConverterCulture$ de-AT��5-      �� 5.      $��	Edit Exam��6   $��7q�6)   ��5/      �� $ ChangeCommand��� 50      $��Delete Exam��6   $��7q�6+   ��51      �� $ DeleteCommand��� 52      $��New Exam��6   $��7q�6(   ��53      �� $ 
NewCommand��� 54      ��6   �� *	errorText+55      56      57      