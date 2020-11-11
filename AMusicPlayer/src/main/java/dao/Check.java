package dao;

import model.MV;
import model.Music;
import model.User;

import java.util.List;

public class Check {
    public static void main(String[] args) {
        /*User user = new User();
        user.setUserName("ly");
        user.setPassword("123");
        user.setAge(21);
        user.setGender("男");
        user.setEmail("675672214@qq.com");

        UserDao userDao = new UserDao();
        userDao.addUser(user);*/

       /*UserDao userDao = new UserDao();
       User user2 = userDao.loginUser("lx","123");
       System.out.println(user2);*/
/*
        Music music = new Music();
        music.setTitle("瘾");
        music.setSinger("audia");
        music.setTime("2019-10-7");
        music.setUrl("music/瘾");
        music.setUserId(1);

        MusicDao musicDao = new MusicDao();*/
        //musicDao.addMusic(music);
       /* Music music = musicDao.selectMusicById(1);
        System.out.println(music);*/
        /*List<Music> list= musicDao.selectMusicByKey("瘾");
        System.out.println(list);*/
       /* List<Music> list = musicDao.selectAllMusic();
        System.out.println(list);*/

       //musicDao.deleteMusicById(1);

        /*musicDao.deleteMusicById(3);*/

       /* musicDao.addLoverMusic(1,2);*/
        //musicDao.removeLoverMusic(1,2);

        //musicDao.addMusic(music);
        //musicDao.addLoverMusic(1,4);
        /*List<Music> list = musicDao.selectLoverMusicByKey("x", 1);
        System.out.println(list);*/

        //musicDao.addMusic(music);
        //musicDao.addLoverMusic(1, 4);

       /*musicDao.deleteMusicById(4);*/


       /* User user = new User();
        UserDao userDao = new UserDao();
        user = userDao.loginUser("ly","123");
        System.out.println(user);*/

        /*MV mv = new MV();
        mv.setUserId(1);
        mv.setMVName("大战唐人街");
        mv.setUrl("1232");
        mv.setTime("156416");

        MVDao mvDao = new MVDao();*/
        //mvDao.addLoverMV(1, 1);
        //mvDao.removeLoverMV(1);
        //mvDao.removeLoverMV(1,1);

       // mvDao.addLoverMV(1,1);
        //mvDao.removeMV(1,1);
        //mvDao.uploadMV(mv);
        UserDao userDao = new UserDao();
        int ret = userDao.deleteUser("ly", "ly");
        System.out.println(ret);
    }
}
