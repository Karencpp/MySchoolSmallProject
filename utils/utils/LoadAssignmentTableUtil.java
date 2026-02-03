package com.ess.utils;

import com.ess.models.Assignment;
import com.ess.services.AssignmentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class LoadAssignmentTableUtil {

    /**
     * 加载任务表格
     * @param taskTable
     * @param taskIdColumn
     * @param reportIdColumn
     * @param gridMemberIdColumn
     * @param assignerIdColumn
     * @param assignmentTimeColumn
     * @param deadlineColumn
     * @param statusColumn
     */
    public static void loadAssignmentTable(


            TableView<Assignment> taskTable,

     TableColumn<Assignment, Integer> taskIdColumn,


     TableColumn<Assignment, Integer> reportIdColumn,


     TableColumn<Assignment, Integer> gridMemberIdColumn,


     TableColumn<Assignment, Integer> assignerIdColumn,


     TableColumn<Assignment, LocalDate> assignmentTimeColumn,


     TableColumn<Assignment, LocalDate> deadlineColumn,


     TableColumn<Assignment, String> statusColumn



    )
    {

        taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("assignment_id"));
        reportIdColumn.setCellValueFactory(new PropertyValueFactory<>("report_id"));
        gridMemberIdColumn.setCellValueFactory(new PropertyValueFactory<>("inspector_id"));
        assignerIdColumn.setCellValueFactory(new PropertyValueFactory<>("assigned_by"));


        assignmentTimeColumn.setCellValueFactory(
                new PropertyValueFactory<>("assigned_at"));

        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));



        // 设置表格数据
        taskTable.setItems(getAssignmentsData());

    }
    public static void loadAssignmentTableOfInspector(


            TableView<Assignment> taskTable,

            TableColumn<Assignment, Integer> taskIdColumn,


            TableColumn<Assignment, Integer> reportIdColumn,


            TableColumn<Assignment, Integer> gridMemberIdColumn,


            TableColumn<Assignment, Integer> assignerIdColumn,


            TableColumn<Assignment, LocalDate> assignmentTimeColumn,


            TableColumn<Assignment, LocalDate> deadlineColumn,


            TableColumn<Assignment, String> statusColumn





    )
    {
        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("assignment_id"));
        reportIdColumn.setCellValueFactory(new PropertyValueFactory<>("report_id"));
        gridMemberIdColumn.setCellValueFactory(new PropertyValueFactory<>("inspector_id"));
        assignerIdColumn.setCellValueFactory(new PropertyValueFactory<>("assigned_by"));


        assignmentTimeColumn.setCellValueFactory(
                new PropertyValueFactory<>("assigned_at"));

        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));



        // 设置表格数据
        taskTable.setItems(getAssignmentsDataOfInspector());

    }

    private static ObservableList<Assignment> getAssignmentsDataOfInspector() {
       ObservableList<Assignment> assignmentsDataOfInspector = FXCollections.observableArrayList(
                new AssignmentService().getAssignmentsByInspector(GlobleData.currentUser.getUserId())
        );

       return assignmentsDataOfInspector;
    }

    private static ObservableList<Assignment> getAssignmentsData() {
       ObservableList<Assignment> assignmentsData = FXCollections.observableArrayList(
                new AssignmentService().getAllAssignments()
        );

       return assignmentsData;
    }

}
