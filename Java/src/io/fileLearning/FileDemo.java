package io.fileLearning;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileDemo{
    public static void main(String[] args) throws IOException {
        //File对象的三种创建方式
        demo1_CreateFile();

        //判断文件/目录
        demo02_Check();

        // Demo3: 创建和删除
        demo3_CreateAndDelete();

        // Demo4: 获取文件信息
        demo4_GetInfo();

        // Demo5: 遍历目录
        demo5_ListFiles();
    }

    private static void demo5_ListFiles() {
        System.out.println("========== Demo5: 遍历目录 ==========");

        File dir = new File("files");

        if (dir.exists() && dir.isDirectory()) {

            // 方式1: list() - 返回文件名字符串数组
            System.out.println("--- 方式1: list() ---");
            String[] names = dir.list();
            if (names != null) {
                for (String name : names) {
                    System.out.println("文件名: " + name);
                }
            }

            // 方式2: listFiles() - 返回File对象数组(推荐!)
            System.out.println("\n--- 方式2: listFiles() ---");
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    String type = file.isDirectory() ? "[目录]" : "[文件]";
                    System.out.println(type + " " + file.getName());
                }
            }

            // 方式3: listFiles(FileFilter) - 过滤文件
            System.out.println("\n--- 方式3: 过滤.txt文件 ---");
            File[] txtFiles = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isFile() && file.getName().endsWith(".txt");
                }
            });

            if (txtFiles != null) {
                for (File file : txtFiles) {
                    System.out.println(file.getName());
                }
            }

            // 方式4: listFiles(FilenameFilter) - 只根据文件名过滤
            System.out.println("\n--- 方式4: 过滤.java文件 ---");
            File[] javaFiles = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".java");
                }
            });
            if (javaFiles != null) {
                for (File file : javaFiles) {
                    System.out.println(file.getName());
                }
            }
        }

        System.out.println();
    }

    private static void demo4_GetInfo() {
        System.out.println("========== Demo4: 获取文件信息 ==========");

        File file = new File("test.txt");

        if (file.exists()) {
            // 文件名
            System.out.println("文件名: " + file.getName());

            // 相对路径(创建时传入的路径)
            System.out.println("相对路径: " + file.getPath());

            // 绝对路径
            System.out.println("绝对路径: " + file.getAbsolutePath());

            // 文件大小(字节)
            System.out.println("文件大小: " + file.length() + " bytes");

            // 最后修改时间(毫秒时间戳)
            long lastModified = file.lastModified();
            System.out.println("最后修改时间: " + lastModified);
            // 转换为日期
            Date date = new Date(lastModified);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("格式化时间: " + sdf.format(date));

            // 父目录
            System.out.println("父目录: " + file.getParent());

            // 是否可读/可写/可执行
            System.out.println("可读: " + file.canRead());
            System.out.println("可写: " + file.canWrite());
            System.out.println("可执行: " + file.canExecute());

            // 是否隐藏
            System.out.println("是否隐藏: " + file.isHidden());
        }

        System.out.println();
    }

    private static void demo3_CreateAndDelete() throws IOException {
        System.out.println("========== Demo3: 创建和删除 ==========");

        // 1. 创建文件
        File file = new File("temp.txt");
        if (!file.exists()) {
            boolean created = file.createNewFile();
            System.out.println("创建文件: " + created);
        } else {
            System.out.println("文件已存在");
        }

        // 2. 创建单级目录
        File dir1 = new File("temp_dir");
        if (!dir1.exists()) {
            boolean created = dir1.mkdir();
            System.out.println("创建单级目录: " + created);
        }

        // 3. 创建多级目录(推荐用这个!)
        File dir2 = new File("c:a\\b\\c");
        if (!dir2.exists()) {
            boolean created = dir2.mkdirs(); // 会自动创建父目录
            System.out.println("创建多级目录: " + created);
        }

        // 4. 删除文件
        File fileToDelete = new File("temp.txt");
        if (fileToDelete.exists()) {
            boolean deleted = fileToDelete.delete();
            System.out.println("删除文件: " + deleted);
        }

        // 5. 删除空目录
        File dirToDelete = new File("temp_dir");
        if (dirToDelete.exists() && dirToDelete.isDirectory()) {
            boolean deleted = dirToDelete.delete();
            System.out.println("删除目录: " + deleted);
        }

        //注意: delete()不能删除非空目录!
        File nonEmptyDir = new File("a");
        boolean deleted = nonEmptyDir.delete();
        System.out.println("删除非空目录: " + deleted); // false

        System.out.println();
    }


    private static void demo02_Check() {
        System.out.println("========== Demo2: 判断文件和目录 ==========");

        File file = new File("test.txt");
        File dir = new File("files");
        File notExist = new File("不存在.txt");

        // 判断是否存在
        System.out.println("test.txt存在吗? " + file.exists());
        System.out.println("不存在.txt存在吗? " + notExist.exists());

        // 判断是文件还是目录
        if (file.exists()) {
            System.out.println("test.txt是文件吗? " + file.isFile());
            System.out.println("test.txt是目录吗? " + file.isDirectory());
        }

        if (dir.exists()) {
            System.out.println("files是文件吗? " + dir.isFile());
            System.out.println("files是目录吗? " + dir.isDirectory());
        }

        //注意: 文件不存在时,isFile()和isDirectory()都返回false
        System.out.println("不存在的文件: isFile=" + notExist.isFile() +
                ", isDirectory=" + notExist.isDirectory());

        System.out.println();
    }

    private static void demo1_CreateFile() {
        System.out.println("========== Demo01 File三种创建方式 ==========");
        //方式1：直接传入路径字符串
        File file1 = new File("test.txt");
        System.out.println("方式1："+file1);

        //方式2：父目录对象+子文件名
        File parent = new File("files");
        File file2 = new File(parent,"test.txt");
        System.out.println("方式2："+file2);

        //方式3：父目录字符串+子文件名
        File file3 = new File("files","test.txt");
        System.out.println("方式3："+file3);

        // 跨平台路径分隔符(重要!)
        // Windows: C:\Users\test\file.txt
        // Linux:   /home/test/file.txt
        // 跨平台写法:
        String path = "files" + File.separator + "test.txt";
        File file4 = new File(path);
        System.out.println("跨平台路径: " + file4);

    }


}