LICENSE = "CLOSED"

SRC_URI = " \
	file://20-etc.preset			\
	file://10-wlan.network			\
	file://bashrc				\
	file://boot.mount			\
	file://profile				\
	file://wpa_supplicant-wlan0.conf	\
"

CONFFILES = "							\
	${sysconfdir}/profile					\
	${sysconfdir}/bash/bashrc				\
	${sysconfdir}/systemd/network/10-wlan.network		\
	${sysconfdir}/wpa_supplicant/wpa_supplicant-wlan0.conf	\
"

FILES_${PN} = "							\
	${sysconfdir}/profile					\
	${sysconfdir}/bash/bashrc				\
	${sysconfdir}/systemd/network/10-wlan.network		\
	${sysconfdir}/wpa_supplicant/wpa_supplicant-wlan0.conf	\
	${systemd_unitdir}/system-preset/20-etc.preset		\
"

do_install() {
	install -d ${D}${sysconfdir}
	install -m 644 ${WORKDIR}/profile			${D}${sysconfdir}/
	install -d ${D}${sysconfdir}/bash
	install -m 644 ${WORKDIR}/bashrc			${D}${sysconfdir}/bash/
	install -d ${D}${sysconfdir}/systemd/network
	install -m 644 ${WORKDIR}/10-wlan.network		${D}${sysconfdir}/systemd/network/
	install -d ${D}${sysconfdir}/wpa_supplicant
	install -m 644 ${WORKDIR}/wpa_supplicant-wlan0.conf	${D}${sysconfdir}/wpa_supplicant/
	install -d ${D}${systemd_unitdir}/system-preset
	install -m 644 ${WORKDIR}/20-etc.preset			${D}${systemd_unitdir}/system-preset/
	install -d ${D}${systemd_system_unitdir}/
	install -m 644 ${WORKDIR}/boot.mount			${D}${systemd_system_unitdir}/
}

PACKAGE_WRITE_DEPS_append = " systemd-systemctl-native"

pkg_postinst_${PN} () {
	if [ -n "$D" ]; then
		OPTS="--root=$D"
	fi
	systemctl $OPTS enable sshd.socket
	install -d $D${sysconfdir}/systemd/system/multi-user.target.wants
	ln -sf ${systemd_system_unitdir}/wpa_supplicant@.service  \
		$D${sysconfdir}/systemd/system/multi-user.target.wants/wpa_supplicant@wlan0.service 

	sed 's,/root:/bin/sh,/root:/bin/bash,' -i $D${sysconfdir}/passwd
}

inherit systemd
SYSTEMD_SERVICE_${PN} = "boot.mount"