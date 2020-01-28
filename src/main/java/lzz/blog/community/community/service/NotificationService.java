package lzz.blog.community.community.service;

import lzz.blog.community.community.dto.NotificationDTO;
import lzz.blog.community.community.dto.PageutilDTO;
import lzz.blog.community.community.enums.NotificationEnum;
import lzz.blog.community.community.enums.NotificationStatusEnum;
import lzz.blog.community.community.exception.CustomizeErrorCode;
import lzz.blog.community.community.exception.CustomizeException;
import lzz.blog.community.community.mapper.NotificationMapper;
import lzz.blog.community.community.model.Notification;
import lzz.blog.community.community.model.NotificationExample;
import lzz.blog.community.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public PageutilDTO list(Long userId, Integer page, Integer size) {
        PageutilDTO<NotificationDTO> pageutilDTO = new PageutilDTO<>();
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
        pageutilDTO.setPageView(totalCount, page, size);
        //分页参数
        Integer offset = size * (pageutilDTO.getPage() - 1);
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        if (notifications.size() == 0) {
            return pageutilDTO;
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<NotificationDTO>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        pageutilDTO.setData(notificationDTOS);
        return pageutilDTO;
    }

    public Long unreadCount(Long id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
