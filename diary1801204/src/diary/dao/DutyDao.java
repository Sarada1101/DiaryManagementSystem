package diary.dao;

import diary.bean.DutyBeans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DutyDao extends DaoBase {

    /**
     * データベースに今日の日付と同じ日誌の情報が登録されているか確認する
     *
     * @param class_code ログインしている学生のクラスコード
     * @param today      今日の日付
     * @return 登録済みならtrue
     */
    public boolean checkTodayDutyRegistered(String class_code, String today) {
        //test
        System.out.println("DutyDao : checkTodayDutyRegistered");
        System.out.println("param : class_code = " + class_code);
        System.out.println("param : today = " + today);

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            this.dbConnect();
            stmt = con.prepareStatement("SELECT * FROM diary_duty WHERE class_code = ? AND insert_date = ?");
            stmt.setString(1, class_code);
            stmt.setString(2, today);
            rs = stmt.executeQuery();

            //もし登録されてなければ
            if (!rs.next()) {
                System.out.println("return : is_registering = false"); //test

                return /* is_registering = */ false;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                this.dbClose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("return : is_registering = true"); //test

        return /* is_registering = */ true;
    }

    /**
     * データベースに日誌の情報を登録(INSERT)する
     *
     * @param duty_beans データベースに登録する日誌の情報
     */
    public void insertDutyToDb(DutyBeans duty_beans) {
        //test
        System.out.println("DutyDao : insertDiaryToDb");
        System.out.println("param : duty_beans = " + duty_beans);
        System.out.println("                    : class_code = " + duty_beans.getClass_code());
        System.out.println("                    : insert_date = " + duty_beans.getInsert_date());
        System.out.println("                    : student_id = " + duty_beans.getStudent_id());
        System.out.println("                    : student_name = " + duty_beans.getStudent_name());

        PreparedStatement stmt = null;

        try {
            this.dbConnect();
            stmt = con.prepareStatement(createInsertSqlSentence());
            stmt = configureValueInPlaceholderOfInsertSqlSentence(stmt, duty_beans);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                this.dbClose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * UPDATE文を作成する
     *
     * @return 作成したUPDATE文
     */
    protected String createInsertSqlSentence() {
        String sql = "INSERT INTO diary_duty (class_code, insert_date, student_id) VALUES (?, ?, ?)";
        return sql;
    }

    /**
     * UPDATE文のプレースホルダーに値を埋め込む
     *
     * @param stmt       プレースホルダーに値を埋め込む前のUPDATE文
     * @param duty_beans プレースホルダーに埋め込む値
     * @return プレースホルダーに値を埋め込んだUPDATE文
     */
    protected PreparedStatement configureValueInPlaceholderOfInsertSqlSentence(PreparedStatement stmt, DutyBeans duty_beans) {
        try {
            stmt.setString(1, duty_beans.getClass_code());
            stmt.setString(2, duty_beans.getInsert_date());
            stmt.setString(3, duty_beans.getStudent_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }


    /**
     * データベースの日誌の情報を入力した情報に更新(UPDATE)する
     *
     * @param duty_beans 更新する日誌の情報
     */
    public void updateDutyToDb(DutyBeans duty_beans) {
        //test
        System.out.println("DutyDao : updateDiaryToDb");
        System.out.println("param : duty_beans = " + duty_beans);
        System.out.println("                    : class_code = " + duty_beans.getClass_code());
        System.out.println("                    : insert_date = " + duty_beans.getInsert_date());
        System.out.println("                    : student_id = " + duty_beans.getStudent_id());
        System.out.println("                    : student_name = " + duty_beans.getStudent_name());

        PreparedStatement stmt = null;

        try {
            this.dbConnect();
            stmt = con.prepareStatement(createUpdateSqlSentence());
            stmt = configureValueInPlaceholderOfUpdateSqlSentence(stmt, duty_beans);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                this.dbClose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * UPDATE文を作成する
     *
     * @return 作成したUPDATE文
     */
    protected String createUpdateSqlSentence() {
        String sql = "UPDATE diary_duty SET class_code = ?, insert_date = ?, student_id = ? WHERE class_code = ? AND insert_date = ?";
        return sql;
    }

    /**
     * UPDATE文のプレースホルダーに値を埋め込む
     *
     * @param stmt       プレースホルダーに値を埋め込む前のUPDATE文
     * @param duty_beans プレースホルダーに埋め込む値
     * @return プレースホルダーに値を埋め込んだUPDATE文
     */
    protected PreparedStatement configureValueInPlaceholderOfUpdateSqlSentence(PreparedStatement stmt, DutyBeans duty_beans) {
        try {
            stmt.setString(1, duty_beans.getClass_code());
            stmt.setString(2, duty_beans.getInsert_date());
            stmt.setString(3, duty_beans.getStudent_id());
            stmt.setString(4, duty_beans.getClass_code());
            stmt.setString(5, duty_beans.getInsert_date());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }


    /**
     * 指定されたカラムを指定された順番にソートした日誌の情報をすべて取得(SELECT)する
     *
     * @param condition   SQL文のWERHE句に指定する条件
     * @param sort_column ソート対象のカラム名
     * @param sort_order  ソートの順番
     * @return 取得した日誌の情報を格納したリスト
     */
    public List<DutyBeans> fetchSortedDutyListFromDb(String condition, String sort_column, String sort_order) {
        //test
        System.out.println("DutyDao : fetchSortedDutyListFromDb");
        System.out.println("param : condition = " + condition);
        System.out.println("param : sort_column = " + sort_column);
        System.out.println("param : sort_order = " + sort_order);

        DutyBeans duty_beans = null;
        List<DutyBeans> list = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            this.dbConnect();
            stmt = con.prepareStatement(createSortSqlSentence(sort_column, sort_order));
            stmt = configureValueInPlaceholderOfSortSqlSentence(stmt, condition);
            rs = stmt.executeQuery();

            list = new ArrayList<>();

            while (rs.next()) {
                duty_beans = new DutyBeans();
                duty_beans.setClass_code(rs.getString("class_code"));
                duty_beans.setInsert_date(rs.getString("insert_date"));
                duty_beans.setStudent_id(rs.getString("student_id"));
                duty_beans.setStudent_name(rs.getString("student_name"));
                list.add(duty_beans);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                this.dbClose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("return : list = " + list); //test

        return list;
    }

    /**
     * 日誌の情報をソートするSELECT文を作成する
     *
     * @param sort_column ソート対象のカラム名
     * @param sort_order  ソートの順番
     * @return 作成したSELECT文
     */
    protected String createSortSqlSentence(String sort_column, String sort_order) {
        String sql = "SELECT * FROM diary_duty d INNER JOIN student s ON d.student_id = s.student_id WHERE d.class_code = ? ORDER BY ";

        boolean is_sort_allowed = false;

        switch (sort_column) {
            case "insert_date":
                sort_column = "d.insert_date";
                is_sort_allowed = true;
                break;
            case "student_id":
                sort_column = "d.student_id";
                is_sort_allowed = true;
                break;
            case "student_name":
                sort_column = "s.student_name";
                is_sort_allowed = true;
                break;
            default:
                is_sort_allowed = false;
                break;
        }

        if (!(sort_order.equals("ASC") || sort_order.equals("DESC"))) {
            is_sort_allowed = false;
        }

        //許可されたカラムかつ予約語が妥当なもの(ASC,DESC)なら
        if (is_sort_allowed) {
            sql += sort_column + " " + sort_order;
        }
        return sql;
    }

    /**
     * ソートするSELECT文のプレースホルダーに値を埋め込む
     *
     * @param stmt      プレースホルダーに値を埋め込む前のUPDATE文
     * @param condition プレースホルダーに埋め込む値
     * @return プレースホルダーに値を埋め込んだSELECT文
     */
    protected PreparedStatement configureValueInPlaceholderOfSortSqlSentence(PreparedStatement stmt, String condition) {
        try {
            stmt.setString(1, condition);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }


    /**
     * 指定された単語をで曖昧検索を行って抽出された日誌の情報をすべて取得(SELECT)する
     *
     * @param condition   SQL文のWHERE句に指定する条件
     * @param search_word 曖昧検索を行う単語
     * @return 取得した日誌の情報を格納したリスト
     */
    public List<DutyBeans> fetchSearchedDutyListFromDb(String condition, String search_word) {
        //test
        System.out.println("DutyDao : fetchSearchedDutyListFromDb");
        System.out.println("param : condition = "   + condition);
        System.out.println("param : search_word = " + search_word);

        DutyBeans duty_beans = null;
        List<DutyBeans> list = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            this.dbConnect();
            stmt = con.prepareStatement(createSearchSqlSentence());
            stmt = configureValueInPlaceholderOfSearchSqlSentence(stmt, condition, search_word);
            rs = stmt.executeQuery();

            list = new ArrayList<>();

            while (rs.next()) {
                duty_beans = new DutyBeans();
                duty_beans.setClass_code     (rs.getString("class_code"));
                duty_beans.setInsert_date    (rs.getString("insert_date"));
                duty_beans.setStudent_id     (rs.getString("student_id"));
                duty_beans.setStudent_name(rs.getString("student_name"));
                list.add(duty_beans);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                this.dbClose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("return : list = " + list); //test

        return list;
    }

    /**
     * 日誌の情報を曖昧検索するSELECT文を作成する
     *
     * @return 作成したSELECT文
     */
    protected String createSearchSqlSentence() {
        String sql = "SELECT * FROM diary_duty d INNER JOIN student s ON d.student_id = s.student_id WHERE (d.insert_date LIKE ? OR d.student_id LIKE ? OR s.student_name LIKE ?) AND d.class_code = ?";
        return sql;
    }

    /**
     * 曖昧検索するSELECT文のプレースホルダーに値を埋め込む
     *
     * @param stmt        プレースホルダーに値を埋め込む前のUPDATE文
     * @param search_word プレースホルダーに埋め込む曖昧検索のための単語
     * @param condition   プレースホルダーに埋め込む値
     * @return プレースホルダーに値を埋め込んだSELECT文
     */
    protected PreparedStatement configureValueInPlaceholderOfSearchSqlSentence(PreparedStatement stmt, String condition, String search_word) {
        try {
            stmt.setString(1, "%" + search_word + "%");
            stmt.setString(2, "%" + search_word + "%");
            stmt.setString(3, "%" + search_word + "%");
            stmt.setString(4, condition);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }
}