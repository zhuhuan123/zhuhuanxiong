

import java.lang.reflect.Field;  
import java.lang.reflect.InvocationTargetException;  
import java.lang.reflect.Method;  
import java.util.Collections;  
import java.util.Comparator;  
import java.util.Date;  
import java.util.List;  
  
/** 
 * ͨ�ù�����֮��������ĳ�������� 
 * @author ����  
 * ��������:http://blog.csdn.net/lk_blog 
 */  
public class SortListUtil {  
    public static final String DESC = "desc";  
    public static final String ASC = "asc";  
  
    /** 
     * ��list�е�Ԫ�ذ���������. 
     *  
     * @param list 
     *            ���򼯺� 
     * @param field 
     *            �����ֶ� 
     * @return 
     */  
    public static List<?> sort(List<?> list, final String field) {  
        return sort(list, field, null);  
    }  
  
    /** 
     * ��list�е�Ԫ�ؽ�������. 
     *  
     * @param list 
     *            ���򼯺� 
     * @param field 
     *            �����ֶ� 
     * @param sort 
     *            ����ʽ: SortList.DESC(����) SortList.ASC(����). 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static List<?> sort(List<?> list, final String field,  
            final String sort) {  
        Collections.sort(list, new Comparator() {  
            public int compare(Object a, Object b) {  
                int ret = 0;  
                try {  
                    Field f = a.getClass().getDeclaredField(field);  
                    f.setAccessible(true);  
                    Class<?> type = f.getType();  
  
                    if (type == int.class) {  
                        ret = ((Integer) f.getInt(a)).compareTo((Integer) f  
                                .getInt(b));  
                    } else if (type == double.class) {  
                        ret = ((Double) f.getDouble(a)).compareTo((Double) f  
                                .getDouble(b));  
                    } else if (type == long.class) {  
                        ret = ((Long) f.getLong(a)).compareTo((Long) f  
                                .getLong(b));  
                    } else if (type == float.class) {  
                        ret = ((Float) f.getFloat(a)).compareTo((Float) f  
                                .getFloat(b));  
                    } else if (type == Date.class) {  
                        ret = ((Date) f.get(a)).compareTo((Date) f.get(b));  
                    } else if (isImplementsOf(type, Comparable.class)) {  
                        ret = ((Comparable) f.get(a)).compareTo((Comparable) f  
                                .get(b));  
                    } else {  
                        ret = String.valueOf(f.get(a)).compareTo(  
                                String.valueOf(f.get(b)));  
                    }  
  
                } catch (SecurityException e) {  
                    e.printStackTrace();  
                } catch (NoSuchFieldException e) {  
                    e.printStackTrace();  
                } catch (IllegalArgumentException e) {  
                    e.printStackTrace();  
                } catch (IllegalAccessException e) {  
                    e.printStackTrace();  
                }  
                if (sort != null && sort.toLowerCase().equals(DESC)) {  
                    return -ret;  
                } else {  
                    return ret;  
                }  
  
            }  
        });  
        return list;  
    }  
  
    /** 
     * ��list�е�Ԫ�ذ�fields��sorts��������, 
     * fields[i]ָ�������ֶ�,sorts[i]ָ������ʽ.���sorts[i]Ϊ����Ĭ�ϰ���������. 
     *  
     * @param list 
     * @param fields 
     * @param sorts 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static List<?> sort(List<?> list, String[] fields, String[] sorts) {  
        if (fields != null && fields.length > 0) {  
            for (int i = fields.length - 1; i >= 0; i--) {  
                final String field = fields[i];  
                String tmpSort = ASC;  
                if (sorts != null && sorts.length > i && sorts[i] != null) {  
                    tmpSort = sorts[i];  
                }  
                final String sort = tmpSort;  
                Collections.sort(list, new Comparator() {  
                    public int compare(Object a, Object b) {  
                        int ret = 0;  
                        try {  
                            Field f = a.getClass().getDeclaredField(field);  
                            f.setAccessible(true);  
                            Class<?> type = f.getType();  
                            if (type == int.class) {  
                                ret = ((Integer) f.getInt(a))  
                                        .compareTo((Integer) f.getInt(b));  
                            } else if (type == double.class) {  
                                ret = ((Double) f.getDouble(a))  
                                        .compareTo((Double) f.getDouble(b));  
                            } else if (type == long.class) {  
                                ret = ((Long) f.getLong(a)).compareTo((Long) f  
                                        .getLong(b));  
                            } else if (type == float.class) {  
                                ret = ((Float) f.getFloat(a))  
                                        .compareTo((Float) f.getFloat(b));  
                            } else if (type == Date.class) {  
                                ret = ((Date) f.get(a)).compareTo((Date) f  
                                        .get(b));  
                            } else if (isImplementsOf(type, Comparable.class)) {  
                                ret = ((Comparable) f.get(a))  
                                        .compareTo((Comparable) f.get(b));  
                            } else {  
                                ret = String.valueOf(f.get(a)).compareTo(  
                                        String.valueOf(f.get(b)));  
                            }  
  
                        } catch (SecurityException e) {  
                            e.printStackTrace();  
                        } catch (NoSuchFieldException e) {  
                            e.printStackTrace();  
                        } catch (IllegalArgumentException e) {  
                            e.printStackTrace();  
                        } catch (IllegalAccessException e) {  
                            e.printStackTrace();  
                        }  
  
                        if (sort != null && sort.toLowerCase().equals(DESC)) {  
                            return -ret;  
                        } else {  
                            return ret;  
                        }  
                    }  
                });  
            }  
        }  
        return list;  
    }  
  
    /** 
     * Ĭ�ϰ��������� 
     *  
     * @param list 
     * @param method 
     * @return 
     */  
    public static List<?> sortByMethod(List<?> list, final String method) {  
        return sortByMethod(list, method, null);  
    }  
  
    @SuppressWarnings("unchecked")  
    public static List<?> sortByMethod(List<?> list, final String method,  
            final String sort) {  
        Collections.sort(list, new Comparator() {  
            public int compare(Object a, Object b) {  
                int ret = 0;  
                try {  
                    Method m = a.getClass().getMethod(method, null);  
                    m.setAccessible(true);  
                    Class<?> type = m.getReturnType();  
                    if (type == int.class) {  
                        ret = ((Integer) m.invoke(a, null))  
                                .compareTo((Integer) m.invoke(b, null));  
                    } else if (type == double.class) {  
                        ret = ((Double) m.invoke(a, null)).compareTo((Double) m  
                                .invoke(b, null));  
                    } else if (type == long.class) {  
                        ret = ((Long) m.invoke(a, null)).compareTo((Long) m  
                                .invoke(b, null));  
                    } else if (type == float.class) {  
                        ret = ((Float) m.invoke(a, null)).compareTo((Float) m  
                                .invoke(b, null));  
                    } else if (type == Date.class) {  
                        ret = ((Date) m.invoke(a, null)).compareTo((Date) m  
                                .invoke(b, null));  
                    } else if (isImplementsOf(type, Comparable.class)) {  
                        ret = ((Comparable) m.invoke(a, null))  
                                .compareTo((Comparable) m.invoke(b, null));  
                    } else {  
                        ret = String.valueOf(m.invoke(a, null)).compareTo(  
                                String.valueOf(m.invoke(b, null)));  
                    }  
  
                    if (isImplementsOf(type, Comparable.class)) {  
                        ret = ((Comparable) m.invoke(a, null))  
                                .compareTo((Comparable) m.invoke(b, null));  
                    } else {  
                        ret = String.valueOf(m.invoke(a, null)).compareTo(  
                                String.valueOf(m.invoke(b, null)));  
                    }  
  
                } catch (NoSuchMethodException ne) {  
                    System.out.println(ne);  
                } catch (IllegalAccessException ie) {  
                    System.out.println(ie);  
                } catch (InvocationTargetException it) {  
                    System.out.println(it);  
                }  
  
                if (sort != null && sort.toLowerCase().equals(DESC)) {  
                    return -ret;  
                } else {  
                    return ret;  
                }  
            }  
        });  
        return list;  
    }  
  
    @SuppressWarnings("unchecked")  
    public static List<?> sortByMethod(List<?> list, final String methods[],  
            final String sorts[]) {  
        if (methods != null && methods.length > 0) {  
            for (int i = methods.length - 1; i >= 0; i--) {  
                final String method = methods[i];  
                String tmpSort = ASC;  
                if (sorts != null && sorts.length > i && sorts[i] != null) {  
                    tmpSort = sorts[i];  
                }  
                final String sort = tmpSort;  
                Collections.sort(list, new Comparator() {  
                    public int compare(Object a, Object b) {  
                        int ret = 0;  
                        try {  
                            Method m = a.getClass().getMethod(method, null);  
                            m.setAccessible(true);  
                            Class<?> type = m.getReturnType();  
                            if (type == int.class) {  
                                ret = ((Integer) m.invoke(a, null))  
                                        .compareTo((Integer) m.invoke(b, null));  
                            } else if (type == double.class) {  
                                ret = ((Double) m.invoke(a, null))  
                                        .compareTo((Double) m.invoke(b, null));  
                            } else if (type == long.class) {  
                                ret = ((Long) m.invoke(a, null))  
                                        .compareTo((Long) m.invoke(b, null));  
                            } else if (type == float.class) {  
                                ret = ((Float) m.invoke(a, null))  
                                        .compareTo((Float) m.invoke(b, null));  
                            } else if (type == Date.class) {  
                                ret = ((Date) m.invoke(a, null))  
                                        .compareTo((Date) m.invoke(b, null));  
                            } else if (isImplementsOf(type, Comparable.class)) {  
                                ret = ((Comparable) m.invoke(a, null))  
                                        .compareTo((Comparable) m.invoke(b,  
                                                null));  
                            } else {  
                                ret = String.valueOf(m.invoke(a, null))  
                                        .compareTo(  
                                                String.valueOf(m  
                                                        .invoke(b, null)));  
                            }  
  
                        } catch (NoSuchMethodException ne) {  
                            System.out.println(ne);  
                        } catch (IllegalAccessException ie) {  
                            System.out.println(ie);  
                        } catch (InvocationTargetException it) {  
                            System.out.println(it);  
                        }  
  
                        if (sort != null && sort.toLowerCase().equals(DESC)) {  
                            return -ret;  
                        } else {  
                            return ret;  
                        }  
                    }  
                });  
            }  
        }  
        return list;  
    }  
  
    /** 
     * �ж϶���ʵ�ֵ����нӿ����Ƿ����szInterface 
     *  
     * @param clazz 
     * @param szInterface 
     * @return 
     */  
    public static boolean isImplementsOf(Class<?> clazz, Class<?> szInterface) {  
        boolean flag = false;  
  
        Class<?>[] face = clazz.getInterfaces();  
        for (Class<?> c : face) {  
            if (c == szInterface) {  
                flag = true;  
            } else {  
                flag = isImplementsOf(c, szInterface);  
            }  
        }  
  
        if (!flag && null != clazz.getSuperclass()) {  
            return isImplementsOf(clazz.getSuperclass(), szInterface);  
        }  
  
        return flag;  
    }  
  
    public static void main(String[] args) throws Exception {  
//        List<Student> list = new ArrayList<Student>();  
//  
//        list.add(new Student(3, "b", 1, new Date(11110000)));  
//        list.add(new Student(1, "c", 3, new Date(44440000)));  
//        list.add(new Student(2, "a", 2, new Date(22210000)));  
//        list.add(new Student(4, "a", 11, new Date(33330000)));  
//        System.out.println("-------ԭ������-------------------");  
//        for (Student stu : list) {  
//            System.out.println(stu.toString());  
//        }  
//  
//        // ��age��������,ע�����������1,2,3,11. ����1,11,2,3(�����String������������������)  
//        SortListUtil.sort(list, "age", null);  
//        System.out.println("---------����Integer������,��age��������-----------------");  
//        for (Student stu : list) {  
//            System.out.println(stu.toString());  
//        }  
//  
//        // ��id����  
//        SortListUtil.sort(list, "id", SortListUtil.DESC);  
//        System.out.println("--------����int�͵���,��id����------------------");  
//        for (Student stu : list) {  
//            System.out.println(stu.toString());  
//        }  
//  
//        // �Ȱ�name��������,�ٰ�id��������  
//        SortListUtil.sort(list, new String[] { "name", "id" }, new String[] {});  
//        System.out  
//                .println("---------���Զ�������ֶ�,�Ȱ�name����,name��ͬʱ�ٰ�id����-----------------");  
//        for (Student stu : list) {  
//            System.out.println(stu.toString());  
//        }  
//  
//        // �Ȱ�name��������,�ٰ�id��������  
//        SortListUtil.sort(list, new String[] { "name", "id" }, new String[] {  
//                SortListUtil.ASC, SortListUtil.DESC });  
//        System.out  
//                .println("---------���Զ�������ֶ�,�Ȱ�name����,name��ͬʱ�ٰ�id����-----------------");  
//        for (Student stu : list) {  
//            System.out.println(stu.toString());  
//        }  
//  
//        // ��birthday����  
//        SortListUtil.sort(list, "birthday");  
//        System.out  
//                .println("---------����ʵ����Comparable�ӿڵĶ�������,��birthday����-----------------");  
//        for (Student stu : list) {  
//            System.out.println(stu.toString());  
//        }  
//  
//        // sortByMethod  
//        SortListUtil.sortByMethod(list, "getId", null);  
//        System.out  
//                .println("---------����sortByMethod,��getId��������-----------------");  
//        for (Student stu : list) {  
//            System.out.println(stu.toString());  
//        }  
    }
}  
