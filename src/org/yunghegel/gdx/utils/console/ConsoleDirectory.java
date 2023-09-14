package org.yunghegel.gdx.utils.console;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.Annotation;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Method;
import org.yunghegel.gdx.utils.console.directory.Directory;
import org.yunghegel.gdx.utils.console.directory.DirectoryDefinition;
import org.yunghegel.gdx.utils.console.directory.DirectoryMember;
import org.yunghegel.gdx.utils.console.directory.DirectoryMemberDefinition;

import java.lang.reflect.Member;
import java.util.*;


public class ConsoleDirectory {

    public Array<Dir> directories;
    public TreeMap<String,Dir> directoryMap;
    public Map<Dir,Array<Member>> members;

    public ConsoleDirectory() {
        directories = new Array<>();
        members = new HashMap<>();
        directoryMap = new TreeMap<>();
        directoryMap.put("root/",new Dir(null,"root","root/","root directory"));
    }

    public class Dir {

        Class directory;
        String name;
        String path;
        String description;
        Dir[] subdirectories;
        Member[] members;
        Dir parent;
        public Dir(Class directory, String name, String path, String description) {
            this.directory = directory;
            this.name = name;
            this.path = path;
            this.description = description;

            if(path!=directoryMap.floorKey(path)) {
                parent = directoryMap.floorEntry(path).getValue();
            }

            LinkedList<String> pathList = new LinkedList<>(Arrays.asList(path.split("/")));
            pathList.removeLast();
            pathList.add(name);
            path = String.join("/",pathList);
            directoryMap.put(path,this);


        }

        public void setSubdirectories(Dir ...dir){
            subdirectories = dir;
        }

        public void setMembers(Member ...member){
            members = member;
        }


        @Override
        public String toString() {
            return "Dir{" +
                    "directory=" + directory +
                    ", name='" + name + '\'' +
                    ", path='" + path + '\'' +
                    ", description='" + description + '\'' +
                    ", subdirectories=" + Arrays.toString(subdirectories) +
                    ", members=" + Arrays.toString(members) +
                    ", parent=" + parent +
                    '}';
        }
    }

    public class Member{

            Method method;
            String name;
            String description;
            Array<String> parameters;

            public Member(Method method, String name, String description, Array<String> parameters) {
                this.method = method;
                this.name = name;
                this.description = description;
                this.parameters = parameters;

            }

        @Override
        public String toString() {
            return "Member{" +
                    "method=" + method +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", parameters=" + parameters +
                    '}';
        }
    }



    public void addDirectory(Class directory) {
        boolean valid = ClassReflection.isAnnotationPresent(directory, Directory.class);
        if (!valid) {
            throw new IllegalArgumentException("Class "+directory.getName()+" is not a valid directory");
        }
        Annotation a = ClassReflection.getAnnotation(directory, Directory.class);
        Directory dir = a.getAnnotation(Directory.class);
        String name = dir.name();
        String path = dir.path();
        String description = dir.description();
        Dir d = new Dir(directory, name, path, description);
        directories.add(d);
        directoryMap.put(path, d);
        scanDirectory(d,directory);

    }



    public void scanDirectory(Dir directory,Class classDir) {

            Array<Method> methods = new Array<>();
            Method[] ms = ClassReflection.getMethods(classDir);
            for (Method m : ms) {
                if (m.isPublic()&&m.isAnnotationPresent(DirectoryMember.class)) {
                    methods.add(m);
                    System.out.println(m);
                }
            }
            Member[] members = new Member[methods.size];
            int memberCount = 0;
            for (Method m : methods) {
                Annotation a = m.getDeclaredAnnotation(DirectoryMember.class);
                DirectoryMember dm = a.getAnnotation(DirectoryMember.class);
                if(dm!=null){
                    memberCount++;
                    String name = dm.name();
                    String description = dm.description();
                    Array<String> parameters = new Array<>();
                    for (String s : dm.paramNames()) {
                        parameters.add(s);
                    }
                    Member member = new Member(m, name, description, parameters);
                    members[memberCount-1] = member;
                }

            }
            directory.setMembers(members);
        }

        public Dir parsePath(String path) {
            return directoryMap.get(path);
        }

        public Dir[] getSubdirectories(Dir directory) {
            Array<Dir> dirs = new Array<>();
            for (Dir d : directories) {
                if (d.path.startsWith(directory.path)&&!d.path.equals(directory.path)) {
                    dirs.add(d);
                }
            }
            return dirs.toArray(Dir.class);
        }

        public Member[] getMembers(Dir directory) {
            Array<Member> members = this.members.get(directory);
            if (members == null) {
                return new Member[0];
            }
            return members.toArray(Member.class);
        }
    }


