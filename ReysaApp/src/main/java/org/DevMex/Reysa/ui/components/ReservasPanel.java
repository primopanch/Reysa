package org.DevMex.Reysa.ui.components;

import java.awt.*;
import javax.swing.*;
import org.DevMex.Reysa.ui.themes.AppFonts;
import org.DevMex.Reysa.ui.themes.AppTheme;

public class ReservasPanel extends JPanel {

    public ReservasPanel() {
        setOpaque(true);
        setBackground(AppTheme.bgMetalicGrey);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createHeader());
        add(Box.createVerticalStrut(20));
        add(createCardsRow());
        add(Box.createVerticalStrut(22));
        add(createScheduleCard());
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel titleBlock = new JPanel();
        titleBlock.setOpaque(false);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Agenda del Taller");
        title.setFont(AppFonts.getOrbitron(34f));
        title.setForeground(AppTheme.textDark);

        JLabel subtitle = new JLabel("Programa y administra las visitas de vehículos para servicio.");
        subtitle.setFont(AppFonts.getRajdhani(14f));
        subtitle.setForeground(new Color(110, 110, 110));

        titleBlock.add(title);
        titleBlock.add(Box.createVerticalStrut(8));
        titleBlock.add(subtitle);

        header.add(titleBlock, BorderLayout.WEST);
        return header;
    }

    private JPanel createCardsRow() {
        JPanel cardsRow = new JPanel(new GridLayout(1, 4, 18, 0));
        cardsRow.setOpaque(false);
        cardsRow.add(createReservaMetricCard("Citas Hoy", "12", Icons.getCalendarIcon()));
        cardsRow.add(createReservaMetricCard("Pendientes de Confirmar", "3", Icons.getCalendarIcon()));
        cardsRow.add(createReservaMetricCard("Completadas", "5", Icons.getCalendarIcon()));
        cardsRow.add(createReservaMetricCard("Disponibilidad Taller", "45%", Icons.getCalendarIcon()));
        return cardsRow;
    }

    private JPanel createScheduleCard() {
        JPanel scheduleCard = new JPanel();
        scheduleCard.setLayout(new BoxLayout(scheduleCard, BoxLayout.Y_AXIS));
        scheduleCard.setOpaque(true);
        scheduleCard.setBackground(Color.WHITE);
        scheduleCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        JPanel scheduleHeader = new JPanel(new BorderLayout());
        scheduleHeader.setOpaque(false);

        JLabel scheduleTitle = new JLabel("Hoy > 10 Abr - 16 Abr, 2026");
        scheduleTitle.setFont(AppFonts.getRajdhani(14f));
        scheduleTitle.setForeground(AppTheme.textDark);

        JButton filterButton = new JButton("Filtrar");
        filterButton.setFont(AppFonts.getRajdhani(12f));
        filterButton.setBackground(new Color(245, 245, 245));
        filterButton.setForeground(AppTheme.textDark);
        filterButton.setFocusPainted(false);
        filterButton.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        scheduleHeader.add(scheduleTitle, BorderLayout.WEST);
        scheduleHeader.add(filterButton, BorderLayout.EAST);
        scheduleCard.add(scheduleHeader);
        scheduleCard.add(Box.createVerticalStrut(18));

        JPanel todayGroup = new JPanel();
        todayGroup.setLayout(new BoxLayout(todayGroup, BoxLayout.Y_AXIS));
        todayGroup.setOpaque(false);
        todayGroup.add(createScheduleSectionLabel("Hoy, Viernes 10 de Abril"));
        todayGroup.add(Box.createVerticalStrut(10));
        todayGroup.add(createReservaItem("08:00 AM", new Color(0, 122, 204)));
        todayGroup.add(Box.createVerticalStrut(10));
        todayGroup.add(createReservaItem("10:30 AM", new Color(0, 153, 51)));
        todayGroup.add(Box.createVerticalStrut(10));
        todayGroup.add(createReservaItem("12:00 PM", new Color(204, 102, 0)));

        JPanel tomorrowGroup = new JPanel();
        tomorrowGroup.setLayout(new BoxLayout(tomorrowGroup, BoxLayout.Y_AXIS));
        tomorrowGroup.setOpaque(false);
        tomorrowGroup.add(createScheduleSectionLabel("Mañana, Sábado 11 de Abril"));
        tomorrowGroup.add(Box.createVerticalStrut(10));
        tomorrowGroup.add(createReservaItem("02:30 PM", new Color(245, 130, 32)));

        scheduleCard.add(todayGroup);
        scheduleCard.add(Box.createVerticalStrut(18));
        scheduleCard.add(tomorrowGroup);
        return scheduleCard;
    }

    private JPanel createReservaMetricCard(String title, String value, ImageIcon icon) {
        JPanel card = new JPanel(new BorderLayout());
        card.setOpaque(true);
        card.setBackground(new Color(245, 245, 245));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(iconLabel, BorderLayout.NORTH);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(AppFonts.getRajdhani(26f));
        valueLabel.setForeground(AppTheme.textDark);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(valueLabel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppFonts.getRajdhani(12f));
        titleLabel.setForeground(new Color(110, 110, 110));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(titleLabel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createScheduleSectionLabel(String text) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        JLabel label = new JLabel(text);
        label.setFont(AppFonts.getRajdhani(13f));
        label.setForeground(new Color(110, 110, 110));
        wrapper.add(label, BorderLayout.WEST);
        return wrapper;
    }

    private JPanel createReservaItem(String time, Color accent) {
        JPanel item = new JPanel(new BorderLayout());
        item.setOpaque(true);
        item.setBackground(new Color(250, 250, 250));
        item.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        item.setPreferredSize(new Dimension(0, 80));

        JPanel accentBar = new JPanel();
        accentBar.setPreferredSize(new Dimension(10, 0));
        accentBar.setBackground(accent);
        item.add(accentBar, BorderLayout.WEST);

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(AppFonts.getRajdhani(14f));
        timeLabel.setForeground(AppTheme.textDark);
        content.add(timeLabel, BorderLayout.WEST);

        item.add(content, BorderLayout.CENTER);
        return item;
    }
}
